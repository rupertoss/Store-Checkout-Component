package com.rupertoss.checkout.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rupertoss.checkout.model.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {

}
