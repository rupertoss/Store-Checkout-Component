package com.rupertoss.checkout.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rupertoss.checkout.model.Promotion;
import com.rupertoss.checkout.repository.PromotionRepository;

@Service
public class PromotionService {

	@Autowired
	private PromotionRepository promotionRepository;
	
	/**
	 * Get a single Promotion entity by code.
	 * 
	 * @param code A String promotion code.
	 * @return A Promotion with given code or discount attribute 0.0 if not valid.
	 */
	public Promotion getByCode(String code) {
		List<Promotion> promotions = new ArrayList<>();
		promotionRepository.findAll().forEach(promotions::add);
		for(Promotion promo: promotions) {
			if(promo.getCode().equals(code)) {
				if (promo.getValidTill().after(Calendar.getInstance())) {
					return promo;
				}
			}
		}
		Promotion promotion = new Promotion();
		promotion.setDiscount(0.0);
		return promotion;
	}
}
