package com.rupertoss.checkout;

import java.io.IOException;
import java.math.BigDecimal;
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
		Map<Integer, Integer> items = new HashMap<>();
		items.put(1, 5);
		items.put(3, 2);
		items.put(4, 3);
		return new Cart(1L, items, new BigDecimal("250.0"));
	}
	
    protected Promotion getPromotion1StubData() {
	    Calendar cal = Calendar.getInstance();
	    cal.set(2019, 01, 30, 12, 0);
		return new Promotion(2,"10%OFF", "Reduces items cost by 10%", new BigDecimal("10.0"), cal);
	}
    
    protected Promotion getPromotion2StubData() {
	    Calendar cal = Calendar.getInstance();
	    cal.set(2017, 01, 30, 12, 0);
		return new Promotion(2,"5%OFF", "Reduces items cost by 5%", new BigDecimal("5.0"), cal);
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
    	return new Item(1, "Product A", new BigDecimal("40.0"), new BigDecimal("35.0"), 3);
	}
    
    protected Item getItem2StubData() {
    	return new Item(2, "Product B", new BigDecimal("20.0"), new BigDecimal("15.0"), 2);
	}
    
    protected Item getItem3StubData() {
    	return new Item(3, "Product C", new BigDecimal("30.0"), new BigDecimal("24.0"), 5);
	}
    
    protected Item getItem4StubData() {
    	return new Item(4, "Product D", new BigDecimal("5.0"), new BigDecimal("4.5"), 15);
	}
    
    protected Item getItem5StubData() {
    	return new Item(5, "Product E", new BigDecimal("25.0"), new BigDecimal("20.0"), 2);
	}
}
