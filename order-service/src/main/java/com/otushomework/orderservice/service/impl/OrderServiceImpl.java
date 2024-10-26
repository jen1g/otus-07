package com.otushomework.orderservice.service.impl;

import com.otushomework.orderservice.dto.DeliveryRequest;
import com.otushomework.orderservice.dto.OrderRequest;
import com.otushomework.orderservice.dto.PaymentRequest;
import com.otushomework.orderservice.dto.WarehouseRequest;
import com.otushomework.orderservice.entity.Order;
import com.otushomework.orderservice.entity.OrderStatus;
import com.otushomework.orderservice.feign.DeliveryClient;
import com.otushomework.orderservice.feign.PaymentClient;
import com.otushomework.orderservice.feign.WarehouseClient;
import com.otushomework.orderservice.repository.OrderRepository;
import com.otushomework.orderservice.service.KafkaMessageProducer;
import com.otushomework.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final PaymentClient paymentClient;
    private final WarehouseClient warehouseClient;
    private final DeliveryClient deliveryClient;
    private final OrderRepository orderRepository;
    private final KafkaMessageProducer kafkaProducer;

    public OrderServiceImpl(PaymentClient paymentClient, WarehouseClient warehouseClient, DeliveryClient deliveryClient,
                            OrderRepository orderRepository, KafkaMessageProducer kafkaProducer) {
        this.paymentClient = paymentClient;
        this.warehouseClient = warehouseClient;
        this.deliveryClient = deliveryClient;
        this.orderRepository = orderRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Transactional
    public void createOrder(String userId, OrderRequest orderRequest) {
        long longUserId = Long.parseLong(userId);
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setUserId(longUserId);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(orderRequest.getTotalAmount());
        orderRepository.save(order);

        String orderId = order.getOrderId();

        try {
            // Шаг 2: Обработка платежа
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setOrderId(orderId);
            paymentRequest.setUserId(longUserId);
            paymentRequest.setAmount(orderRequest.getTotalAmount());
            paymentClient.processPayment(paymentRequest);

            // Шаг 3: Резервирование товаров
            WarehouseRequest warehouseRequest = new WarehouseRequest();
            warehouseRequest.setOrderId(orderId);
            warehouseRequest.setItems(orderRequest.getItems());
            warehouseClient.reserveProducts(warehouseRequest);

            // Шаг 4: Планирование доставки
            DeliveryRequest deliveryRequest = new DeliveryRequest();
            deliveryRequest.setOrderId(orderId);
            deliveryRequest.setTimeslotId(orderRequest.getTimeslotId());
            deliveryClient.scheduleDelivery(deliveryRequest);

            // Шаг 5: Обновляем статус заказа на "COMPLETED"
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);

            // Шаг 6: Отправляем уведомление
            kafkaProducer.sendMessage("order-notifications", "Заказ успешно обработан. Номер заказа: " + order.getOrderId());

        } catch (Exception e) {
            // Компенсация
            handleCompensation(orderId, orderRequest, longUserId, e);
            throw new RuntimeException("Обработка заказа не удалась: " + e.getMessage());
        }
    }

    private void handleCompensation(String orderId, OrderRequest orderRequest, long longUserId, Exception originalException) {
        // Откат в обратном порядке
        try {
            // Отменяем доставку, если была запланирована
            try {
                deliveryClient.cancelDelivery(orderId);
            } catch (Exception ex) {
                // Логируем ошибку, но продолжаем
            }

            // Освобождаем товары, если были зарезервированы
            try {
                WarehouseRequest warehouseRequest = new WarehouseRequest();
                warehouseRequest.setOrderId(orderId);
                warehouseRequest.setItems(orderRequest.getItems());
                warehouseClient.dereservationProducts(warehouseRequest);
            } catch (Exception ex) {
                // Логируем ошибку, но продолжаем
            }

            // Возвращаем платеж, если был обработан
            try {
                PaymentRequest paymentRequest = new PaymentRequest();
                paymentRequest.setOrderId(orderId);
                paymentRequest.setUserId(longUserId);
                paymentRequest.setAmount(orderRequest.getTotalAmount());
                paymentClient.refundPayment(paymentRequest);
            } catch (Exception ex) {
                // Логируем ошибку, но продолжаем
            }
            // Обновляем статус заказа на "FAILED"
            Order order = orderRepository.findById(orderId).orElse(null);
            if (order != null) {
                order.setStatus(OrderStatus.FAILED);
                orderRepository.save(order);
            }
        } catch (Exception ex) {
            // Логируем ошибки при компенсации
            // В продакшене следует реализовать более надежную обработку ошибок
            kafkaProducer.sendMessage("order-notifications", "Заказ отменен.");
        }
    }
}
