package com.rupertoss.checkout.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rupertoss.checkout.model.Promotion;

@Repository
public interface PromotionRepository extends CrudRepository<Promotion, Integer>{

}
