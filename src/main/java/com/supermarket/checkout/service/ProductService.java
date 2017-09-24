package com.supermarket.checkout.service;

import com.supermarket.checkout.domain.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO save(ProductDTO ProductDTO);

    List<ProductDTO> findAll();

    ProductDTO findOne(Long id);

    void delete(Long id);

    public ProductDTO checkOutProducts(String productList);

}
