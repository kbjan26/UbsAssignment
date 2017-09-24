package com.supermarket.checkout.service;

import com.supermarket.checkout.domain.dto.ProductOfferDTO;

import java.util.List;

public interface ProductOfferService {

    ProductOfferDTO save(ProductOfferDTO ProductOfferDTO);

    List<ProductOfferDTO> findAll();

    ProductOfferDTO findOne(Long id);

    void delete(Long id);

}
