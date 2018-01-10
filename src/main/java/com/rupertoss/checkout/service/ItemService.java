package com.rupertoss.checkout.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rupertoss.checkout.model.Item;
import com.rupertoss.checkout.repository.ItemRepository;

@Service
public class ItemService {
	
	// The Spring Data repository for Item entities.
	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * Get all Item entities.
	 * 
	 * @return A List of Item objects.
	 */
	public List<Item> getAll() {
		List<Item> items = new ArrayList<>();
		itemRepository.findAll().forEach(items::add);
		return items;
	}
	
	/**
	 * Get a single Item entity by primary key identifier.
	 * 
	 * @param id An Integer primary key identifier.
	 * @return An Item or null if none found.
	 */
	public Item getById(Integer id) {
		return itemRepository.findOne(id);
	}
	
	/**
	 * Calculates cost of single Item entity by multiplying required quantity
	 * 	by its price or special price if required quantity is greater or equal
	 * 	special quantity.
	 * 	
	 * @param id An Integer primary key identifier of Item entity.
	 * @param quantity An Integer quantity.
	 * @return A Double representing cost of Item.
	 */
	public double calculateItemCost(Integer id, Integer quantity) {
		Item item = itemRepository.findOne(id);
		if(quantity >= item.getSpecialQuantity()) {
			return (item.getSpecialPrice() * quantity);
		} else {
			return item.getPrice() * quantity;
		}
	}
}
