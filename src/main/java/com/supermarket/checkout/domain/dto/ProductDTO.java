package com.supermarket.checkout.domain.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO implements Serializable {

    private Long id;
    private String name;
    private Long price;
    private ProductOfferDTO offer;
    private String productList;
    private Long totalPrice;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ProductOfferDTO getOffer() {
        return offer;
    }

    public void setOffer(ProductOfferDTO offer) {
        this.offer = offer;
    }

    public String getProductList() {
        return productList;
    }

    public void setProductList(String productList) {
        this.productList = productList;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }




}
