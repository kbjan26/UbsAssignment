package com.supermarket.checkout.web.rest.checkout;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CheckoutResource.PATH)
public class CheckoutResource {

    public static final String PATH="api/checkout";
}
