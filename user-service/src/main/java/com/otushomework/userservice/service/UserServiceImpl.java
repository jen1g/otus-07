package com.otushomework.userservice.service;

import com.otushomework.userservice.entity.User;
import com.otushomework.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Value("${billing.service.url}")
    private String billingServiceUrl;

    private final UserRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User saveUser(User user) {
       return repository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void updateUserById(User updatedUser) {
        repository.save(updatedUser);
    }

    @Override
    public void deleteUserById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
       return repository.findByUsernameAndPassword(username, password);
    }

    @Override
    public boolean existsByUsername(String username) {
       return repository.existsByUsername(username);
    }

    @Override
    public void createBillingAccount(User user) {
        String url = billingServiceUrl + "/billing?userId=" + user.getId();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Account created for user with ID: " + user.getId());
            } else {
                System.out.println("Failed to create account for user with ID: " + user.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
