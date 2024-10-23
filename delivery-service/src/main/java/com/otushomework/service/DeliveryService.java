package com.otushomework.service;

import com.otushomework.repository.DeliveryRepository;
import com.otushomework.request.DeliveryRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Transactional
    public void scheduleDelivery(DeliveryRequest deliveryRequest) {
//        boolean isAvailable = !deliveryRepository.existsByTimeslot(deliveryRequest.getTimeslot());
//        if (!isAvailable) {
//            throw new RuntimeException("Delivery timeslot not available");
//        }
//        Delivery delivery = new Delivery();
//        delivery.setOrderId(deliveryRequest.getOrderId());
//        delivery.setTimeslot(deliveryRequest.getTimeslot());
//        deliveryRepository.save(delivery);
    }

    @Transactional
    public void cancelDelivery(Long orderId) {
//        Delivery delivery = deliveryRepository.findByOrderId(orderId)
//                .orElseThrow(() -> new RuntimeException("Delivery not found"));
//        deliveryRepository.delete(delivery);
    }
}