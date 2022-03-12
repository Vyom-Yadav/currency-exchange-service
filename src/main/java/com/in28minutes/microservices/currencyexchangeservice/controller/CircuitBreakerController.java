package com.in28minutes.microservices.currencyexchangeservice.controller;

import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @GetMapping("/sample-api")
//    @Retry(name = "sample-api", fallbackMethod = "hardCodedResponse")
//    @CircuitBreaker(name = "default", fallbackMethod = "hardCodedResponse")
    @RateLimiter(name = "default")
    @Bulkhead(name = "default")
    // 10s => 10000 calls to the sample-api
    public String sampleApi() {
//        logger.info("===========>Sample API call received");
//        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(
//            "http://localhost:8080/some-dummy", String.class);
//        return responseEntity.getBody();
        return "sample-api";
    }

    public String hardCodedResponse(Exception ex) {
        return "fallback-response";
    }
}
