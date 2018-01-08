package com.rupertoss.checkout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rupertoss.checkout.model.Cart;
import com.rupertoss.checkout.repository.CartRepository;

@Service
public class CartService {
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ItemService itemService;
	
	public Cart getCart(long id) {
		return cartRepository.findOne(id);
	}
	
	public void addCart(Cart cart) {
		cart.setValue(calculateCartValue(cart));
		cartRepository.save(cart);
	}
	
	public void updateCart(long id, Cart cart) {
		Cart cartToUpdate = cartRepository.findOne(id);
		if(cartToUpdate != null) {
			
			cartToUpdate.setItems(cart.getItems());
			cartToUpdate.setValue(calculateCartValue(cart));
			
			cartRepository.save(cartToUpdate);	
		}
	}
	
	public void deleteCart(long id) {
		Cart cartToDelete = cartRepository.findOne(id);
		if(cartToDelete != null) {
			cartRepository.delete(id);
		}
	}
	
	public double calculateCartValue(Cart cart) {
		if (cart.getItems().isEmpty()) {
			cart.setValue(0);
		} else {
			cart.getItems().forEach((k,v) -> {
				cart.setValue(cart.getValue() + itemService.calculateItemValue(k, v));
			}); 
		}
		return cart.getValue();
	}
}
