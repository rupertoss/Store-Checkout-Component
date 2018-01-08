package com.rupertoss.checkout.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.rupertoss.checkout.model.Cart;
import com.rupertoss.checkout.repository.CartRepository;

public class CartService {
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ItemService itemService;
	
	public Cart getCart(long id) {
		return cartRepository.findOne(id);
	}
	
	public void addCart(Cart cart) {
		cartRepository.save(cart);
	}
	
	public void updateCart(long id, Cart cart) {
		Cart cartToUpdate = cartRepository.findOne(id);
		if(cartToUpdate != null) {
			
			cartToUpdate.setId(cart.getId());
			cartToUpdate.setItems(cart.getItems());
			cartToUpdate.setValue(cart.getValue());
			
			cartRepository.save(cartToUpdate);	
		}
	}
	
	public void deleteCart(long id) {
		Cart cartToDelete = cartRepository.findOne(id);
		if(cartToDelete != null) {
			cartRepository.delete(id);
		}
	}
	
	public void calculateValue(Cart cartToCaluculate) {
		Cart cart = cartRepository.findOne(cartToCaluculate.getId());
		if(cart != null) {
			if (cart.getItems().isEmpty()) {
				cart.setValue(0);
			} else {
				cart.getItems().forEach((k,v) -> {
					cart.setValue(cart.getValue() + itemService.calculateValue(k, v));
				}); 
			}
		}
	}
}
