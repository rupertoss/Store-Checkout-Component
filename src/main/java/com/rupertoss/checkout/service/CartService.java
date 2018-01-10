package com.rupertoss.checkout.service;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rupertoss.checkout.model.Cart;
import com.rupertoss.checkout.repository.CartRepository;

@Service
public class CartService {
	
	// The Spring Data repository for Cart entities.
	@Autowired
	CartRepository cartRepository;
	
	// The ItemService business service.
	@Autowired
	ItemService itemService;
	
	/**
	 * Get a single Cart entity by primary key identifier.
	 * 
	 * @param id A Long primary key identifier.
	 * @return A Cart or null if none found.
	 */
	public Cart getById(Long id) {
		return cartRepository.findOne(id);
	}
	
	/**
	 * Persists a Cart entity in the data store.
	 * Throws an EntityExistsException if Cart id attribute not equals null.
	 * 
	 * @param cart A Cart object to be persisted.
	 * @return The persisted Cart entity.
	 */
	public Cart create(Cart cart) {
		
		if(cart.getId() != null)
			throw new EntityExistsException("The id attribute must be null to persist a new entity.");
		cart.setValue(calculateCartValue(cart));
		return cartRepository.save(cart);
	}
	
	/**
	 * Updates a previously persisted Cart entity in the data store.
	 * Throws NoResultException if there is no Cart stored with given Cart id attribute.
	 * 
	 * @param cart A Cart object to be updated.
	 * @return The updated Cart entity.
	 */
	public Cart update(Cart cart) {
		
		Cart cartToUpdate = cartRepository.findOne(cart.getId());
		
		if(cartToUpdate == null) 
			throw new NoResultException("Requested entity not found.");
		
		
		cartToUpdate.setItems(cart.getItems());
		cartToUpdate.setValue(calculateCartValue(cart));
		
		return cartRepository.save(cartToUpdate);	
	}
	
	/**
	 * Removes a previously persisted Cart entity from the data store.
	 * Throws NoResultException if there is no Cart stored with given Cart id attribute.
	 * 
	 * @param id A Long primary key identifier.
	 */
	public void delete(Long id) {
		Cart cartToDelete = cartRepository.findOne(id);
		if(cartToDelete == null) 
			throw new NoResultException("Requested entity not found.");
		
		cartRepository.delete(id);
	}
	
	/**
	 * Calculate value of single Cart entity by summing all Item's costs in the Cart.
	 * If Cart is empty returns 0.
	 * 
	 * @param cart A Cart object to calculate its value.
	 * @return Double representing value of Cart.
	 */
	public double calculateCartValue(Cart cart) {
		cart.setValue(0);
		if (!cart.getItems().isEmpty()) {
			cart.getItems().forEach((k,v) -> {
				cart.setValue(cart.getValue() + itemService.calculateItemCost(k, v));
			}); 
		}
		return cart.getValue();
	}
}
