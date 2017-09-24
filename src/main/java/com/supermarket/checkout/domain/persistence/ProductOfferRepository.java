package com.supermarket.checkout.domain.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOfferRepository extends JpaRepository<ProductOfferEntity, Long> {
}
