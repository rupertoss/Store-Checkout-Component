package com.rupertoss.checkout.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rupertoss.checkout.model.Promotion;
import com.rupertoss.checkout.repository.PromotionRepository;

@Service
public class PromotionService {

	// The PromotionRepository business service.
	@Autowired
	private PromotionRepository promotionRepository;
	
	/**
	 * Get a single Promotion entity by code.
	 * 
	 * @param code A String promotion code.
	 * @return A Promotion with given code or null if none found.
	 */
	public Promotion getByCode(String code) {
		List<Promotion> promotions = new ArrayList<>();
		promotionRepository.findAll().forEach(promotions::add);
		for(Promotion promo: promotions) {
			if(promo.getCode().equals(code))
				return promo;
		}
		return null;
	}
}
