package com.in28minutes.microservices.currencyexchangeservice.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.microservices.currencyexchangeservice.beans.CurrencyExchange;
import com.in28minutes.microservices.currencyexchangeservice.dao.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {

    private final Logger logger = Logger.getLogger(getClass().getName());

    private final Environment environment;

    private final CurrencyExchangeRepository currencyExchangeRepository;

    @Autowired
    public CurrencyExchangeController(Environment environment,
        CurrencyExchangeRepository currencyExchangeRepository) {
        this.environment = environment;
        this.currencyExchangeRepository = currencyExchangeRepository;
    }

    @GetMapping("currency-exchange/from/{cur1}/to/{cur2}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String cur1,
        @PathVariable String cur2) {
        logger.info(() -> "request received from " + cur1 + " to " + cur2);
        CurrencyExchange currencyExchange
            = currencyExchangeRepository.findByFromAndTo(cur1, cur2);
        if (currencyExchange == null) {
            throw new RuntimeException(
                "Unable to find data for " + cur1 + " to " + cur2);
        }
        currencyExchange.setEnvironment(
            environment.getProperty("local.server.port"));
        return currencyExchange;
    }

}
