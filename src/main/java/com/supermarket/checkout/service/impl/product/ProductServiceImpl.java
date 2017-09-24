package com.supermarket.checkout.service.impl.product;

import com.supermarket.checkout.domain.dto.ProductDTO;
import com.supermarket.checkout.domain.persistence.ProductEntity;
import com.supermarket.checkout.domain.persistence.ProductOfferEntity;
import com.supermarket.checkout.domain.persistence.ProductRepository;
import com.supermarket.checkout.mapper.ProductMapper;
import com.supermarket.checkout.mapper.ProductOfferMapper;
import com.supermarket.checkout.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {


    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductOfferMapper productOfferMapper;

    @Transactional
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save productEntity : {}", productDTO);
        ProductEntity productEntity = productMapper.productDTOToProductEntity(productDTO);
        ProductOfferEntity productOfferEntity=productOfferMapper.productOfferDTOToProductOfferEntity(productDTO.getOffer());
        productEntity.setOffer(productOfferEntity);
        productEntity = productRepository.save(productEntity);
        ProductDTO result = productMapper.productEntityToProductDTO(productEntity);
        return result;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        log.debug("Request to get all ProductEntity");
        List<ProductDTO> result = productRepository.findAll().stream()
                .map(productMapper::productEntityToProductDTO)
                .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    @Transactional(readOnly = true)
    public ProductDTO findOne(Long id) {
        log.debug("Request to get ProductEntity : {}", id);
        ProductEntity ProductEntity = productRepository.findOne(id);
        ProductDTO ProductDTO = productMapper.productEntityToProductDTO(ProductEntity);
        return ProductDTO;
    }

    @Transactional
    public ProductDTO checkOutProducts(String productList) {
        ProductDTO productDTO = new ProductDTO();
        Long totalCost = 0L;
        Map<String, Integer> productEntryMap = new HashMap<>();
        for (char c : productList.toCharArray()) {
            productEntryMap.merge(String.valueOf(c), 1, Integer::sum);
        }
        List<ProductEntity> productEntities = productRepository.findAll();
        for (String productKey : productEntryMap.keySet()) {
            boolean isPresent = productEntities.stream()
                    .anyMatch(p -> p.getName().equalsIgnoreCase(productKey));
            if (isPresent) {
                totalCost = calculateProductPrice(totalCost, productEntryMap, productEntities, productKey);
            }
        }
        productDTO.setTotalPrice(productDTO.getPrice()!=null
                ?productDTO.getPrice():0+totalCost);
        return productDTO;
    }

    private Long calculateProductPrice(Long totalCost, Map<String, Integer> productEntryMap
            , List<ProductEntity> productEntities, String productKey) {
        Optional<ProductEntity> productEntity = productEntities.stream()
                .filter(p -> p.getName().equalsIgnoreCase(productKey))
                .findFirst();
        if (productEntity.isPresent()) {
            Integer totalItems = productEntryMap.get(productKey);
            if (productEntity.get().getOffer() != null) {
                ProductOfferEntity productOfferEntity = productEntity.get().getOffer();
                if ((productOfferEntity != null) && (totalItems > productOfferEntity.getUnits())) {
                    Long offerUnits = productOfferEntity.getUnits();
                    Long wholePriceUnits = totalItems % offerUnits;
                    Long offerPriceUnits = totalItems / offerUnits;
                    totalCost += (wholePriceUnits * productEntity.get().getPrice())
                            + (offerPriceUnits * productOfferEntity.getOfferPrice());
                } else {
                    totalCost = totalItems * productEntity.get().getPrice();
                }
            } else {
                totalCost = totalItems * productEntity.get().getPrice();
            }
        }

        return totalCost;
    }

    public void delete(Long id) {
        log.debug("Request to delete ProductEntity : {}", id);
        productRepository.delete(id);
    }

}
