package com.rupertoss.checkout.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rupertoss.checkout.model.Item;
import com.rupertoss.checkout.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping
	public List<Item> getAllItems() {
		return itemService.getAllItems();
	}
	
	@GetMapping("/{id}")
	public Item getItems(@PathVariable(value = "id") int id) {
		return itemService.getItem(id);
	}
}
