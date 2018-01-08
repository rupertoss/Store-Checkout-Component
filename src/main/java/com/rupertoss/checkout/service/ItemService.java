package com.rupertoss.checkout.service;

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
	
	public List<Item> getAllItems() {
		List<Item> items = new ArrayList<>();
		itemRepository.findAll().forEach(items::add);
		return items;
	}
	
	public Item getItem(int id) {
		return itemRepository.findOne(id);
	}
	
	public double calculateItemValue(int id, int quantity) {
		Item item = itemRepository.findOne(id);
		if(quantity >= item.specialQuantity) {
			return (item.specialPrice) * quantity;
		} else {
			return item.price * quantity;
		}
	}
	
}
