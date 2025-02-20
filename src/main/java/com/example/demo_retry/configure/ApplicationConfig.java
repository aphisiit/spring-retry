package com.example.demo_retry.configure;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Set;

@Configuration
@EnableRetry(proxyTargetClass=true)
public class ApplicationConfig {

    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Bean
    public RestTemplate restTemplate() {
        Duration duration = Duration.ofSeconds(6000L);
        return restTemplateBuilder()
        .setReadTimeout(duration)
        .setConnectTimeout(duration)
        .build();
    }

}