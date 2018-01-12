package com.rupertoss.checkout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupertoss.checkout.model.Cart;
import com.rupertoss.checkout.model.Item;
import com.rupertoss.checkout.model.Promotion;

public abstract class AbstractControllerTest {
	
    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
	
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }
	
    protected Cart getCart1StubData() {
		Cart cart = new Cart();
		cart.setId(1L);
		Map<Integer, Integer> items = new HashMap<>();
		items.put(1, 5);
		items.put(3, 2);
		items.put(4, 3);
		cart.setItems(items);
		cart.setValue(250);
		return cart;
	}
	
    protected Promotion getPromotion1StubData() {
		Promotion promotion = new Promotion();
		promotion.setId(1);
		promotion.setCode("Reduces items cost by 10%");
		promotion.setDiscount(10);
	    Calendar cal = Calendar.getInstance();
	    cal.set(2019, 01, 30, 12, 0);
		promotion.setValidTill(cal);
		promotion.setDescription("Promo");
		return promotion;
	}
    
    protected Promotion getPromotion2StubData() {
		Promotion promotion = new Promotion();
		promotion.setId(2);
		promotion.setCode("5%OFF");
		promotion.setDiscount(5);
	    Calendar cal = Calendar.getInstance();
	    cal.set(2017, 01, 30, 12, 0);
		promotion.setValidTill(cal);
		promotion.setDescription("Reduces items cost by 5%");
		return promotion;
	}
	
    protected List<Item> getItemListStubData() {
    	List<Item> items = new ArrayList<>();
    	items.add(getItem1StubData());
    	items.add(getItem2StubData());
    	items.add(getItem3StubData());
    	items.add(getItem4StubData());
    	items.add(getItem5StubData());
    	return items;
    }
	
    protected Item getItem1StubData() {
		Item item = new Item();
		item.setId(1);
		item.setDescription("Product A");
		item.setPrice(40.0);
		item.setSpecialPrice(35.0);
		item.setSpecialQuantity(3);
		return item;
	}
    
    protected Item getItem2StubData() {
		Item item = new Item();
		item.setId(2);
		item.setDescription("Product B");
		item.setPrice(20.0);
		item.setSpecialPrice(15.0);
		item.setSpecialQuantity(2);
		return item;
	}
    
    protected Item getItem3StubData() {
		Item item = new Item();
		item.setId(3);
		item.setDescription("Product C");
		item.setPrice(30.0);
		item.setSpecialPrice(24.0);
		item.setSpecialQuantity(5);
		return item;
	}
    
    protected Item getItem4StubData() {
		Item item = new Item();
		item.setId(4);
		item.setDescription("Product D");
		item.setPrice(5.0);
		item.setSpecialPrice(4.5);
		item.setSpecialQuantity(15);
		return item;
	}
    
    protected Item getItem5StubData() {
		Item item = new Item();
		item.setId(5);
		item.setDescription("Product E");
		item.setPrice(25.0);
		item.setSpecialPrice(20.0);
		item.setSpecialQuantity(2);
		return item;
	}
}
