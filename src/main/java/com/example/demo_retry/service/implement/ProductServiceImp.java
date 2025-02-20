package com.example.demo_retry.service.implement;

import com.example.demo_retry.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.Map;
import java.util.Objects;

@Service
public class ProductServiceImp implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

    @Autowired
    private RestTemplate restTemplate;


    @Retryable(
            retryFor = { HttpServerErrorException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 10000)
    )
    @Override
    public Map<String, Object> getProduct() {
        logger.warn("Retry number : {}", Objects.requireNonNull(RetrySynchronizationManager.getContext()).getRetryCount());
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(null, headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange("https://dummyjson.com/products/1", HttpMethod.POST, entity, Map.class);
        return (Map<String, Object>) responseEntity.getBody();
    }

    @Recover
    public Map<String, Object> recover(HttpServerErrorException e) {
        logger.error("Something's went wrong with HttpServerErrorException : {} - {} 😫", e.getRawStatusCode(), e.getStatusText());
        return Map.of("message", "Something's went wrong with HttpServerErrorException.BadGateway😫");
    }

}