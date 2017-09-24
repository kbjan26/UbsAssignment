package com.supermarket.checkout.domain.persistence;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@EqualsAndHashCode(exclude="product")
@ToString(exclude="product")
@Table(name = "PRODUCT_OFFER")
public class ProductOfferEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "units")
    private Long units;

    @Column(name = "price")
    private Long offerPrice;

    @OneToOne(mappedBy = "offer")
    private ProductEntity product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUnits() {
        return units;
    }

    public void setUnits(Long units) {
        this.units = units;
    }

    public Long getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(Long offerPrice) {
        this.offerPrice = offerPrice;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
