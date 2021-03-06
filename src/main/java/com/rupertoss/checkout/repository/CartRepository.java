package com.rupertoss.checkout.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rupertoss.checkout.model.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

}

