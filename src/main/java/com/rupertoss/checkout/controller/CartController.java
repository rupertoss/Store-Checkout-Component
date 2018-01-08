package com.rupertoss.checkout.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rupertoss.checkout.model.Cart;
import com.rupertoss.checkout.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	
	@GetMapping("/{id}")
	public Cart getCart(@PathVariable(value = "id") long id) {
		return cartService.getCart(id);
	}
	
	@PostMapping
	public void addCart(@Valid @RequestBody Cart cart) {
		cartService.addCart(cart);
	}
	
	@PutMapping("/{id}")
	public void updateCart(@PathVariable(value = "id") long id, @Valid @RequestBody Cart cart) {
		cartService.updateCart(id, cart);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCart(@PathVariable(value = "id") long id) {
		cartService.deleteCart(id);
	}
	
	
}
