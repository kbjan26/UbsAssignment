package com.supermarket.checkout.web.rest.product;

import com.supermarket.checkout.domain.dto.ProductOfferDTO;
import com.supermarket.checkout.service.ProductOfferService;
import com.supermarket.checkout.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ProductOfferController.PATH)
public class ProductOfferController {

    public static final String PATH = "api/product/offer";

    private final Logger log = LoggerFactory.getLogger(ProductOfferController.class);

    @Autowired
    private ProductOfferService productOfferService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductOfferDTO> createProduct(@RequestBody ProductOfferDTO productOfferDTO) throws URISyntaxException {
        log.debug("REST request to save Product : {}", productOfferDTO);
        if (productOfferDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("Product", "id exists", "A new Product cannot already have an ID")).body(null);
        }
        ProductOfferDTO result = productOfferService.save(productOfferDTO);
        return ResponseEntity.created(new URI("/api/add/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("Product", result.getId().toString()))
                .body(result);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductOfferDTO> updateProduct(@RequestBody ProductOfferDTO productOfferDTO) throws URISyntaxException {
        log.debug("REST request to update Product : {}", productOfferDTO);
        if (productOfferDTO.getId() == null) {
            return createProduct(productOfferDTO);
        }
        ProductOfferDTO result = productOfferService.save(productOfferDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("Product", productOfferDTO.getId().toString()))
                .body(result);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductOfferDTO> getAllProduct() {
        log.debug("REST request to get all Product");
        return productOfferService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductOfferDTO> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        ProductOfferDTO ProductOfferDTO = productOfferService.findOne(id);
        return Optional.ofNullable(ProductOfferDTO)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productOfferService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("Product", id.toString())).build();
    }

}
