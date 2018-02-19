package com.rupertoss.checkout.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rupertoss.checkout.model.Item;
import com.rupertoss.checkout.repository.ItemRepository;

@Service
public class ItemService {
	
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
	 * @return A BidDecimal representing cost of Item.
	 */
	public BigDecimal calculateItemCost(Integer id, Integer quantity) {
		if (id == null) {
			return BigDecimal.ZERO;
		}
		
		Item item = itemRepository.findOne(id);
		
		if(quantity >= item.getSpecialQuantity()) {
			return (item.getSpecialPrice().multiply(new BigDecimal(quantity)));
		} else {
			return item.getPrice().multiply(new BigDecimal(quantity));
		}
	}
}
