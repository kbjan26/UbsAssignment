package com.supermarket.checkout.web.rest.product;

import com.supermarket.checkout.domain.dto.ProductDTO;
import com.supermarket.checkout.service.ProductService;
import com.supermarket.checkout.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ProductController.PATH)
public class ProductController {

    public static final String PATH = "api/product";

    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Inject
    private ProductService productService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to save Product : {}", productDTO);
        if (productDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(
                    HeaderUtil.createFailureAlert("Product", "id exists",
                            "A new Product cannot already have an ID")).body(null);
        }
        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.created(new URI("/api/add/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("Product", result.getId().toString()))
                .body(result);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to update Product : {}", productDTO);
        if (productDTO.getId() == null) {
            return createProduct(productDTO);
        }
        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("Product", productDTO.getId().toString()))
                .body(result);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getAllProduct() {
        log.debug("REST request to get all Product");
        return productService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        ProductDTO ProductDTO = productService.findOne(id);
        return Optional.ofNullable(ProductDTO)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> checkout(@RequestBody ProductDTO productDTO) {
        log.debug("REST request to checkout and total");
        ProductDTO result = productService.checkOutProducts(productDTO.getProductList());
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("Total price of all items =", result.getTotalPrice().toString()))
                .body(result);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("Product", id.toString())).build();
    }

}
