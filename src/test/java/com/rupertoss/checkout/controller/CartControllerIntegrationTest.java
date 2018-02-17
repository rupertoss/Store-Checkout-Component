package com.rupertoss.checkout.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.rupertoss.checkout.AbstractControllerTest;
import com.rupertoss.checkout.Application;
import com.rupertoss.checkout.model.Cart;
import com.rupertoss.checkout.model.Promotion;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Transactional
@SqlGroup({
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
})
public class CartControllerIntegrationTest extends AbstractControllerTest {
	
	private MockMvc mvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testGetCart() throws Exception{
		Long id = new Long(1);
		Cart cart = getCart1StubData();
		
		String json = mapToJson(cart);
		String uri = "/api/carts/{id}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to match", content.equals(json));
	}
	
	@Test
	public void testGetCartNotFound() throws Exception {
		Long id = Long.MAX_VALUE;
		
		String uri = "/api/carts/{id}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected HTTP status 404", 404, status);
		Assert.assertTrue("failure - expected HTTP body to be empty", content.length() == 0);
	}
	
	@Test
	public void testGetCartWithPromotion() throws Exception {
		Long id = new Long(1);
		Cart cart = getCart1StubData();

		String code = "10%OFF";
		Promotion promotion = getPromotion1StubData();
		
		String uri = "/api/carts/{id}/{code}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id, code).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		cart.setValue(cart.getValue() * (100 - promotion.getDiscount())/100);
		String json = mapToJson(cart);
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to match", content.equals(json));
	}
	
	@Test
	public void testGetCartWithPromotionNonFoundCart() throws Exception {
		Long id = Long.MAX_VALUE;
		
		String code = "10%OFF";
		
		String uri = "/api/carts/{id}/{code}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id, code).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected HTTP status 404", 404, status);
		Assert.assertTrue("failure - expected HTTP body to be empty", content.trim().length() == 0);
	}
	
	@Test
	public void testGetCartWithPromotionNonFoundPromotion() throws Exception {
		Long id = new Long(1);
		Cart cart = getCart1StubData();
		
		String code = "X";
		
		String uri = "/api/carts/{id}/{code}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id, code).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		String json = mapToJson(cart);
		
		Assert.assertEquals("failure - expected HTTP status 409", 409, status);
		Assert.assertTrue("failure - expected HTTP response body to match", content.equals(json));
	}
	
	@Test
	public void testGetCartWithPromotionNoLongerValid() throws Exception {
		Long id = new Long(1);
		Cart cart = getCart1StubData();
		
		String code = "5%OFF";
		
		String uri = "/api/carts/{id}/{code}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id, code).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		String json = mapToJson(cart);
		
		Assert.assertEquals("failure - expected HTTP status 409", 409, status);
		Assert.assertTrue("failure - expected HTTP response body to match", content.equals(json));
	}
	
	@Test
	public void testCreateCart() throws Exception {
		String uri = "/api/carts";
		
		Cart cart = getCart1StubData();
		cart.setId(null);
		String inputJson = mapToJson(cart);
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
							.content(inputJson))
						.andReturn();
		
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        cart.setId(5L);
        inputJson = mapToJson(cart);
        
        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue("failure - expected HTTP response body to match", content.equals(inputJson));
	}
	
	@Test
	public void testUpdateCart() throws Exception {
		Cart cart = getCart1StubData();
		
		Map<Integer, Integer> items = new HashMap<Integer, Integer>();
		items.put(1, 1);
		cart.setItems(items);
		cart.setValue(40);
		
		Long id = new Long(1);
				
		String uri = "/api/carts/{id}";
		String inputJson = mapToJson(cart);
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders
								.put(uri, id).contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(inputJson))
								.andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to match", content.equals(inputJson));
	}
	
	@Test
	public void testDeleteCart() throws Exception {
		Long id = new Long(1);
		
		String uri = "/api/carts/{id}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected HTTP status 204", 204, status);
		Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);
	}
}
