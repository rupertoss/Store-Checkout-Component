package com.rupertoss.checkout.service;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rupertoss.checkout.Application;
import com.rupertoss.checkout.model.Cart;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@SqlGroup({
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
})
public class CartServiceTest {

	@Autowired
	CartService cartService;
	
	@Test
	public void testGetById() {
		Long id = new Long(3);
		
		Cart cart = cartService.getById(id);
		
		Assert.assertNotNull("failure - expected not null", cart);
		Assert.assertEquals("failure - expected id attribute match", id, cart.getId());
	}
	
	@Test
	public void testGetByIdNotFound() {
		Long id = Long.MAX_VALUE;
		
		Cart cart = cartService.getById(id);
		
		Assert.assertNull("failure - expected null" , cart);
	}
	
	@Test
	public void testCreateCartEmpty() {
		Cart cart = new Cart();
		
		Map<Integer, Integer> items = new HashMap<>();
		cart.setItems(items);
		
		Cart createdCart = cartService.create(cart);
		
		Assert.assertNotNull("failure - expected not null", createdCart);
		Assert.assertNotNull("failure - expected id attribute not null", createdCart.getId());
		Assert.assertNotNull("failure - expected items attribute not null", createdCart.getItems());
		Assert.assertNotNull("failure - expected value attribute not null", createdCart.getValue());
		Assert.assertEquals("failure - expected items attribute match", items, createdCart.getItems());
		Assert.assertEquals("failure - expected value attrubute match", 0, createdCart.getValue(), 0.001);
	}
	
	@Test
	public void testCreateCartNotEmpty() {
		Cart cart = new Cart();
		
		Map<Integer, Integer> items = new HashMap<>();
		items.put(1, 1);
		items.put(3, 5);
		cart.setItems(items);
		
		Cart createdCart = cartService.create(cart);
		
		Assert.assertEquals("failure - expected items attribute match", items, createdCart.getItems());
		Assert.assertEquals("failure - expected value attribute match", 160.0, createdCart.getValue(), 0.001);
	}
	
	@Test
	public void testCreateCartWithId() {
		Exception exception = null;
		
		Cart cart = new Cart();
		cart.setId(Long.MAX_VALUE);
		cart.setItems(new HashMap<Integer, Integer>());
		
		try {
			cartService.create(cart);
		} catch (EntityExistsException e) {
			exception = e;
		}
		
		Assert.assertNotNull("failure - expected exception", exception);
		Assert.assertTrue("failure - expected EntityExistsException", exception instanceof EntityExistsException);
	}
	
	@Test
	public void testUpdateCart() {
		Long id = new Long(2);
		
		Cart cart = cartService.getById(id);
		
		double cartValue = cart.getValue();
		
		Assert.assertNotNull("failure - expected not null", cart);
		
		Map<Integer, Integer> updatedItems = cart.getItems();
		updatedItems.put(1, 1);
		
		Cart updatedCart = cartService.update(cart);
		
		Assert.assertNotNull("failure - expected not null", updatedCart);
		Assert.assertEquals("failure - expected id attribute match", id, updatedCart.getId());
		Assert.assertEquals("failure - expected items attribute match", updatedItems, updatedCart.getItems());
		Assert.assertEquals("failure - expected value attrubute match", cartValue + 40, updatedCart.getValue(), 0.001);
	}
	
	@Test
	public void testUpdateNotFound() {
		Exception exception = null;
		
		Cart cart = new Cart();
		cart.setId(Long.MAX_VALUE);
		cart.setItems(new HashMap<Integer, Integer>());
		
		try {
			cartService.update(cart);
		} catch (NoResultException e) {
			exception = e;
		}
		
		Assert.assertNotNull("failure - expected exception", exception);
		Assert.assertTrue("failure - expected NoResultException", exception instanceof NoResultException);
	}
	
	@Test
	public void testDelete() {
		Long id = new Long(1);
		
		Cart cart = cartService.getById(id);
		
		Assert.assertNotNull("failure - expected not null", cart);
		
		cartService.delete(id);
		
		Cart deletedCart = cartService.getById(id);
		
		Assert.assertNull("failure - expected null", deletedCart);
	}
	
	@Test
	public void testDeleteNotFound() {
		Exception exception = null;
		
		Cart cart = new Cart();
		cart.setId(Long.MAX_VALUE);
		
		try {
			cartService.delete(cart.getId());
		} catch (NoResultException e) {
			exception = e;
		}
		
		Assert.assertNotNull("failure - expected exception", exception);
		Assert.assertTrue("failure - expected NoResultException", exception instanceof NoResultException);
	}
	
	@Test
	public void testCalculateCartValueEmpty() {
		Cart cart = new Cart();
		
		Map<Integer, Integer> items = new HashMap<>();
		cart.setItems(items);
		
		Cart emptyCart = cartService.create(cart);
		
		Assert.assertNotNull("failure - expected not null", emptyCart);
		Assert.assertEquals("failure - expected value attribute match", 0, emptyCart.getValue(), 0.001);
	}
	
	@Test
	public void testCalculateCartValue() {
		Cart cart = new Cart();
		
		Map<Integer, Integer> items = new HashMap<>();
		items.put(1, 1);
		items.put(3, 5);
		cart.setItems(items);
		
		cart = cartService.create(cart);
		
		Assert.assertNotNull("failure - expected not null", cart);
		Assert.assertEquals("failure - expected value attribute match", 160.0, cart.getValue(), 0.001);
	}
}
