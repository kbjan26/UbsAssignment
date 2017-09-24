package com.supermarket.checkout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@ComponentScan("com.supermarket.checkout")
public class SuperMarketApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SuperMarketApp.class, args);
    }
}
