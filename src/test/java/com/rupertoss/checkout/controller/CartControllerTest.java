package com.rupertoss.checkout.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;

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

import com.rupertoss.checkout.AbstractControllerTest;
import com.rupertoss.checkout.Application;
import com.rupertoss.checkout.model.Cart;
import com.rupertoss.checkout.model.Promotion;
import com.rupertoss.checkout.service.CartService;
import com.rupertoss.checkout.service.PromotionService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Transactional
public class CartControllerTest extends AbstractControllerTest {
	
	
	private MockMvc mvc;
	
	@Mock
	CartService cartService;
	
	@Mock
	PromotionService promotionService;
	
	@InjectMocks
	CartController cartController;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(cartController).build();
	}
	
	@Test
	public void testGetCart() throws Exception{
		Long id = new Long(1);
		Cart cart = getCart1StubData();
		
		when(cartService.getById(id)).thenReturn(cart);
		
		String uri = "/api/carts/{id}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(cartService, times(1)).getById(id);
		
		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);
	}
	
	@Test
	public void testGetCartNotFound() throws Exception {
		Long id = Long.MAX_VALUE;
		
		when(cartService.getById(id)).thenReturn(null);
		
		String uri = "/api/carts/{id}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(cartService, times(1)).getById(id);
		
		Assert.assertEquals("failure - expected HTTP status 404", 404, status);
		Assert.assertTrue("failure - expected HTTP body to be empty", content.length() == 0);
	}
	
	@Test
	public void testGetCartWithPromotion() throws Exception {
		Long id = new Long(1);
		Cart cart = getCart1StubData();
		
		String code = "10%OFF";
		Promotion promotion = getPromotion1StubData();
		cart = new Cart(cart.getId(), cart.getItems(), new BigDecimal("36"));
		
		when(cartService.getById(id)).thenReturn(cart);
		
		when(promotionService.getByCode(code)).thenReturn(promotion);
		
		when(cartService.calculateCartValueWithPromotion(cart, promotion)).thenReturn(cart);
		
		String uri = "/api/carts/{id}/{code}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id, code).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(cartService, times(1)).getById(id);
		verify(promotionService, times(1)).getByCode(code);
		verify(cartService, times(1)).calculateCartValueWithPromotion(cart, promotion);
		
		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP body to have a value", content.trim().length() > 0);
	}
	
	@Test
	public void testGetCartWithPromotionNonFoundCart() throws Exception {
		Long id = Long.MAX_VALUE;
		
		String code = "10%OFF";
		
		when(cartService.getById(id)).thenReturn(null);
		
		String uri = "/api/carts/{id}/{code}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id, code).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(cartService, times(1)).getById(id);
		
		Assert.assertEquals("failure - expected HTTP status 404", 404, status);
		Assert.assertTrue("failure - expected HTTP body to be empty", content.trim().length() == 0);
	}
	
	@Test
	public void testGetCartWithPromotionNonFoundPromotion() throws Exception {
		Long id = new Long(1);
		Cart cart = getCart1StubData();
		
		String code = "X";
		
		when(cartService.getById(id)).thenReturn(cart);
		
		when(promotionService.getByCode(code)).thenReturn(new Promotion(0,null,null, BigDecimal.ZERO, null));
		
		String uri = "/api/carts/{id}/{code}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id, code).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(cartService, times(1)).getById(id);
		verify(promotionService, times(1)).getByCode(code);
		
		Assert.assertEquals("failure - expected HTTP status 409", 409, status);
		Assert.assertTrue("failure - expected HTTP body to have a value", content.trim().length() > 0);
	}
	
	@Test
	public void testGetCartWithPromotionNoLongerValid() throws Exception {
		Long id = new Long(1);
		Cart cart = getCart1StubData();
		
		String code = "5%OFF";
		
		when(cartService.getById(id)).thenReturn(cart);
		
		when(promotionService.getByCode(code)).thenReturn(new Promotion(0,null,null, BigDecimal.ZERO, null));
		
		String uri = "/api/carts/{id}/{code}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id, code).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(cartService, times(1)).getById(id);
		verify(promotionService, times(1)).getByCode(code);
		
		Assert.assertEquals("failure - expected HTTP status 409", 409, status);
		Assert.assertTrue("failure - expected HTTP body to have a value", content.trim().length() > 0);
	}
	
	@Test
	public void testCreateCart() throws Exception {
		Cart cart = getCart1StubData();
		
		when(cartService.create(any(Cart.class))).thenReturn(cart);
		
		String uri = "/api/carts";
		String inputJson = mapToJson(cart);
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders
								.post(uri).contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(inputJson))
								.andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(cartService, times(1)).create(any(Cart.class));
		
		Assert.assertEquals("failure - expected HTTP status 201", 201, status);
		Assert.assertTrue("failure - epxtected HTTP body to have a value", content.trim().length() > 0);
		
		Cart createdCart = mapFromJson(content, Cart.class);
		
		Assert.assertNotNull("failure - expected entity not null", createdCart);
		Assert.assertNotNull("failure - expected id attribute not null", createdCart.getId());
		Assert.assertNotNull("failure - expected items attribute not null", createdCart.getItems());
		Assert.assertNotNull("failure - expected value attribute not null", createdCart.getValue());
		Assert.assertEquals("failure - expected items attribute match", cart.getItems(), createdCart.getItems());
		Assert.assertEquals("failure - expected value attrubute match", cart.getValue(), createdCart.getValue());
	}
	
	@Test
	public void testUpdateCart() throws Exception {
		Long id = getCart1StubData().getId();
		Cart cart = new Cart(getCart1StubData().getId(), new HashMap<Integer, Integer>(), getCart1StubData().getValue());
						
		when(cartService.update(any(Cart.class))).thenReturn(cart);
		
		String uri = "/api/carts/{id}";
		String inputJson = mapToJson(cart);
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders
								.put(uri, id).contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(inputJson))
								.andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(cartService, times(1)).update(any(Cart.class));
		
		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - epxtected HTTP body to have a value", content.trim().length() > 0);
		
		Cart updatedCart = mapFromJson(content, Cart.class);
		
		Assert.assertNotNull("failure - expected entity not null", updatedCart);
		Assert.assertNotNull("failure - expected items attribute not null", updatedCart.getItems());
		Assert.assertNotNull("failure - expected value attribute not null", updatedCart.getValue());
		Assert.assertEquals("failure - expected id attribute match", cart.getId(), updatedCart.getId());
		Assert.assertEquals("failure - expected items attribute match", cart.getItems(), updatedCart.getItems());
		Assert.assertEquals("failure - expected value attrubute match", cart.getValue(), updatedCart.getValue());
	}
	
	@Test
	public void testUpdateCartInternalError() throws Exception {
		Cart cart = getCart1StubData();
		Long id = new Long(1);
		
		when(cartService.update(any(Cart.class))).thenReturn(null);
		
		String uri = "/api/carts/{id}";
		String inputJson = mapToJson(cart);
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders
								.put(uri, id).contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(inputJson))
								.andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(cartService, times(1)).update(any(Cart.class));
		
		Assert.assertEquals("failure - expected HTTP status 500", 500, status);
		Assert.assertTrue("failure - epxtected HTTP body to be empty", content.trim().length() == 0);
	}
	
	@Test
	public void testDeleteCart() throws Exception {
		Long id = new Long(1);
		
		String uri = "/api/carts/{id}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(cartService, times(1)).delete(id);
		
		Assert.assertEquals("failure - expected HTTP status 204", 204, status);
		Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);
	}
}
