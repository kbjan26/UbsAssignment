package com.supermarket.checkout.mapper;

import com.supermarket.checkout.domain.dto.ProductOfferDTO;
import com.supermarket.checkout.domain.persistence.ProductOfferEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductOfferMapper {


    ProductOfferDTO productOfferEntityToProductOfferDTO(ProductOfferEntity productOfferEntity);

    ProductOfferEntity productOfferDTOToProductOfferEntity(ProductOfferDTO productOfferDTO);

}
