package com.rupertoss.checkout.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rupertoss.checkout.model.Cart;
import com.rupertoss.checkout.model.Promotion;
import com.rupertoss.checkout.service.CartService;
import com.rupertoss.checkout.service.PromotionService;

@RestController
@RequestMapping("api/carts")
public class CartController {
	
	// The CartService business service.
	@Autowired
	private CartService cartService;
	
	//	The PromotionService business service.
	@Autowired
	private PromotionService promotionService;
	
	/**
	 * Web service endpoint to fetch a single Cart entity by primary key identifier.
	 * If found, the Cart is returned as JSON with HTTP status 200.
	 * If not found, the service return an empty response body with HTTP status 404.
	 * 
	 * @param id A Long URL path variable containing the Cart primary key identifier.
	 * @return A ResponseEntity containing a single Cart object, if found,
	 * 			and a HTTP status code as described above.
	 */
	@GetMapping(value = "api/carts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cart> getCart(@PathVariable(value = "id") Long id) {
		Cart cart = cartService.getById(id);
		if(cart == null) {
			return new ResponseEntity<Cart>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}
	
	/**
	 * Web service endpoint to fetch a single Cart entity by primary key with Promotion code.
	 * If Cart not found, the service return an empty response body with HTTP status 404.
	 * If Promotion not found, the regularly priced Cart is returned as JSON with HTTP status 404. 
	 * If Promotion found, but no longer valid, the regularly priced Cart is returned as JSON with HTTP status 409.
	 * If Promotion found and valid, the discounted Cart is returned as JSON with HTTP status 200.
	 * 
	 * @param id A Long URL path variable containing primary key identifier.
	 * @param code A String URL path variable containing Promotion code.
	 * @return A ResponseEntity containing a single Cart object, if found, 
	 * 			in dependence of Promotion and HTTP status code as described above.
	 */
	@GetMapping("api/carts/{id}/{code}")
	public ResponseEntity<Cart> getCartWithPromotion(@PathVariable(value = "id") Long id, @PathVariable(value = "code") String code) {

		Cart cart = cartService.getById(id);
			if(cart == null) {
				return new ResponseEntity<Cart>(HttpStatus.NOT_FOUND);
			}
			
		Promotion promotion = promotionService.getByCode(code);
			if(promotion == null) {
				return new ResponseEntity<Cart>(cart, HttpStatus.NOT_FOUND);
			}
			if(promotion.getValidTill().after(new Date())) {
				return new ResponseEntity<Cart>(cart, HttpStatus.CONFLICT);
			}
		cart.setValue(cart.getValue() * (promotion.getDiscount() / 100));
		
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}
	
	/**
	 * Web service endpoint to create a single Cart entity. The HTTP request body
	 * 	is expected to contain a Cart object in JSON format. The Cart is persisted
	 * 	in the data repository.
	 * If created successfully, the persisted Cart is returned as JSON with HTTP status 201.
	 * If not created successfully, the service returns an empty response body
	 * 	with HTTP status 500.
	 * 
	 * @param cart The Cart object to be created.
	 * @return A ResponseEntity containing a single Cart object, if created successfully,
	 * 			and a HTTP status code as described above.
	 */
	@PostMapping(value = "api/carts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cart> createCart(@Valid @RequestBody Cart cart) {
		Cart savedCart = cartService.create(cart);
		return new ResponseEntity<Cart>(savedCart, HttpStatus.CREATED);
	}
	
	/**
	 * Web service endpoint to update a single Cart entity. The HTTP request body
	 * 	is expected to contain a Cart object in JSON format. The Cart is updated
	 * 	in the data repository.
	 * If updated successfully, the persisted Cart is returned as JSON with HTTP status 200.
	 * If not found, the service returns an empty response body and HTTP status 404.
	 * If not updated successfully, the service returns an empty response body with HTTP status 500.
	 *  
	 * @param cart The Cart object to be updated.
	 * @return A ReponseEntity containing a single Cart object, if updated successfully,
	 * 			and a HTTP status code as described above.
	 */
	@PutMapping(value = "api/carts/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cart> updateCart(@Valid @RequestBody Cart cart) {
		Cart updatedCart = cartService.update(cart);
		if(updatedCart == null) {
			return new ResponseEntity<Cart>(updatedCart, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
		return new ResponseEntity<Cart>(updatedCart, HttpStatus.OK);
	}
	
	/**
	 * Web service endpoint to delete a single Cart entity. The HTTP request body is empty.
	 * 	The primary key identifier of the Cart to be deleted is supplied in the URL as a path variable.
	 * If deleted successfully, the service returns an empty response body with HTTP status 204.
	 * If not deleted successfully, the service returns an empty response body with HTTP status 500.
	 * 
	 * @param id A Long URL path variable containing the Cart primary key identifier.
	 * @return A ResponseEntity with an empty response body and a HTTP status code as described above.
	 */
	@DeleteMapping("api/carts/{id}")
	public ResponseEntity<Cart> deleteCart(@PathVariable(value = "id") Long id) {
		cartService.delete(id);
		return new ResponseEntity<Cart>(HttpStatus.NO_CONTENT);
	}
	
	
}
