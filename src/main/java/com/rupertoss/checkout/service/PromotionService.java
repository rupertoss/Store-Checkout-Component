package com.rupertoss.checkout.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rupertoss.checkout.model.Promotion;
import com.rupertoss.checkout.repository.PromotionRepository;

@Service
public class PromotionService {

	@Autowired
	private PromotionRepository promotionRepository;
	
	public Promotion getPromotion(String code) {
		List<Promotion> promotions = new ArrayList<>();
		promotionRepository.findAll().forEach(promotions::add);
		for(Promotion promo: promotions) {
			if(promo.getCode() == code)
				return promo;
		}
		return null;
	}
}
