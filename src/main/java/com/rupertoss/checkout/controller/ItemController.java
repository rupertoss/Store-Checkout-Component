package com.rupertoss.checkout.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rupertoss.checkout.model.Item;
import com.rupertoss.checkout.service.ItemService;

@RestController
@RequestMapping("api/items")
public class ItemController {
	
	// The ItemService business service.
	@Autowired
	private ItemService itemService;
	
	/**
	 * Web service endpoint to fetch all Items entities. The service returns the list
	 * 	of Items entities as JSON.
	 * 
	 * @return A ResponseEntity containing a List of Items objects.
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Item>> getAllItems() {
		return new ResponseEntity<List<Item>>(itemService.getAll(), HttpStatus.OK);
	}
	
	/**
	 * Web service endpoint to fetch a single Item entity by primary key identifier.
	 * If found, the Item is returned as JSON with HTTP status 200.
	 * If not found, the service return an empty response body with HTTP status 404.
	 * 
	 * @param id A Integer URL path variable containing the Item primary key identifier.
	 * @return A ResponseEntity containing a single Item object, if found,
	 * 			and a HTTP status code as described above.
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Item> getItem(@PathVariable(value = "id") Integer id) {
		 Item item = itemService.getById(id);
		 if(item == null) {
			 return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
		 }
		 return new ResponseEntity<Item>(item, HttpStatus.OK);
	}
}
