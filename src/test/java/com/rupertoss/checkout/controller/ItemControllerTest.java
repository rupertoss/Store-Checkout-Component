package com.rupertoss.checkout.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.rupertoss.checkout.Application;
import com.rupertoss.checkout.model.Item;
import com.rupertoss.checkout.service.ItemService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Transactional
public class ItemControllerTest {

	public MockMvc mvc;
	
	@Mock
	public ItemService itemService;
	
	@InjectMocks
	public ItemController itemController;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		mvc = MockMvcBuilders.standaloneSetup(itemController).build();
	}
	
	@Test
	public void testGetAllItems() throws Exception {
		
		List<Item> items = getItemListStubData();
		
		when(itemService.getAll()).thenReturn(items);
		
		String uri = "/api/items";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(itemService, times(1)).getAll();
		
		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);
	}
	
	@Test
	public void testGetItemById() throws Exception {
		Integer id = new Integer(1);
		Item item = getItemStubData();
		
		when(itemService.getById(id)).thenReturn(item);
		
		String uri = "/api/items/{id}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(itemService, times(1)).getById(id);
		
		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);
	}
	
	@Test 
	public void testGetItemByIdNotFound() throws Exception {
		Integer id = Integer.MAX_VALUE;
		
		when(itemService.getById(id)).thenReturn(null);
		
		String uri = "/api/items/{id}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(itemService, times(1)).getById(id);
		
		Assert.assertEquals("failure - expected HTTP status 404", 404, status);
		Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);
	}
	
	private List<Item> getItemListStubData() {
		List<Item> items = new ArrayList<>();
		items.add(getItemStubData());
		return items;
	}
	
	private Item getItemStubData() {
		Item item = new Item();
		item.setId(1);
		item.setDescription("Product A");
		item.setPrice(12.0);
		item.setSpecialPrice(10.0);
		item.setSpecialQuantity(10);
		return item;
		
	}
	
}
