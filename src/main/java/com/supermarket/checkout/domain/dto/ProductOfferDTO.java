package com.supermarket.checkout.domain.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductOfferDTO implements Serializable {
    private Long id;
    private Long units;
    private Long offerPrice;
}
