package com.example.demo_retry.service;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import java.util.Map;

public interface ProductService {

    Map<String, Object> getProduct() throws HttpClientErrorException, HttpStatusCodeException;
}