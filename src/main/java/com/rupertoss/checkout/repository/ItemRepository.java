package com.rupertoss.checkout.repository;

import org.springframework.data.repository.CrudRepository;

import com.rupertoss.checkout.model.Item;

public interface ItemRepository extends CrudRepository<Item, Integer> {

}
