package com.rupertoss.checkout.repository;

import org.springframework.data.repository.CrudRepository;

import com.rupertoss.checkout.model.Cart;

public interface CartRepository extends CrudRepository<Cart, Long> {

}

