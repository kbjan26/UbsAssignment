package com.supermarket.checkout.mapper;

import com.supermarket.checkout.domain.dto.ProductDTO;
import com.supermarket.checkout.domain.persistence.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductOfferMapper.class})
public interface ProductMapper {

    ProductDTO productEntityToProductDTO(ProductEntity productEntity);

    ProductEntity productDTOToProductEntity(ProductDTO productDTO);

}
