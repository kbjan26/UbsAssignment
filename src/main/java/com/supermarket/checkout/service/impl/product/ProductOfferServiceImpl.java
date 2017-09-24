package com.supermarket.checkout.service.impl.product;

import com.supermarket.checkout.domain.dto.ProductOfferDTO;
import com.supermarket.checkout.domain.persistence.ProductOfferEntity;
import com.supermarket.checkout.domain.persistence.ProductOfferRepository;
import com.supermarket.checkout.mapper.ProductOfferMapper;
import com.supermarket.checkout.service.ProductOfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductOfferServiceImpl implements ProductOfferService {


    private final Logger log = LoggerFactory.getLogger(ProductOfferServiceImpl.class);

    @Autowired
    private ProductOfferRepository productOfferRepository;

    @Autowired
    private ProductOfferMapper productOfferMapper;

    public ProductOfferDTO save(ProductOfferDTO ProductOfferDTO) {
        log.debug("Request to save productOfferEntity : {}", ProductOfferDTO);
        ProductOfferEntity productOfferEntity = productOfferMapper.productOfferDTOToProductOfferEntity(ProductOfferDTO);
        productOfferEntity = productOfferRepository.save(productOfferEntity);
        ProductOfferDTO result = productOfferMapper.productOfferEntityToProductOfferDTO(productOfferEntity);
        return result;
    }

    @Transactional(readOnly = true)
    public List<ProductOfferDTO> findAll() {
        log.debug("Request to get all ProductOfferEntity");
        List<ProductOfferDTO> result = productOfferRepository.findAll().stream()
                .map(productOfferMapper::productOfferEntityToProductOfferDTO)
                .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    @Transactional(readOnly = true)
    public ProductOfferDTO findOne(Long id) {
        log.debug("Request to get ProductOfferEntity : {}", id);
        ProductOfferEntity ProductOfferEntity = productOfferRepository.findOne(id);
        ProductOfferDTO ProductOfferDTO = productOfferMapper.productOfferEntityToProductOfferDTO(ProductOfferEntity);
        return ProductOfferDTO;
    }

    public void delete(Long id) {
        log.debug("Request to delete ProductOfferEntity : {}", id);
        productOfferRepository.delete(id);
        productOfferRepository.delete(id);
    }

}
