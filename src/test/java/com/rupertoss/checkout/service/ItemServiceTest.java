package com.rupertoss.checkout.service;

import java.util.List;

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
import com.rupertoss.checkout.model.Item;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@SqlGroup({
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
})
public class ItemServiceTest {

	@Autowired
	private ItemService itemService;
	
	@Test
	public void testGetAll() {
		List<Item> items = itemService.getAll();
		
		Assert.assertNotNull("failure - expected not null", items);
		Assert.assertEquals("failure - expected list size", 5, items.size());
	}
	
	@Test
	public void testGetById() {
		Integer id = new Integer(2);
		
		Item item = itemService.getById(id);
		
		Assert.assertNotNull("failure - expected not null", item);
		Assert.assertEquals("failure - expected id attribute match", id, item.getId());
	}
	
	@Test 
	public void testGetByIdNotFound() {
		Integer id = Integer.MAX_VALUE;
		
		Item item = itemService.getById(id);
		
		Assert.assertNull("failure - expected null", item);
	}
	
	@Test
	public void testCalculateItemCostRegularPrice() {
		Integer id = new Integer(3);
		Integer quantity = new Integer(4);
		
		Double regularCost = itemService.calculateItemCost(id, quantity);
		
		Assert.assertEquals("failure - expected Item cost with regular price", 120.0, regularCost, 0.001);
	}
	
	@Test
	public void testCaluculateItemCostSpecialPrice() {
		Integer id = new Integer(3);
		Integer quantity = new Integer(5);
		
		Double specialCost = itemService.calculateItemCost(id, quantity);
		
		Assert.assertEquals("failure - expected Item cost with special price", 120.0, specialCost, 0.001);
	}
}
