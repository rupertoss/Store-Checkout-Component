package com.rupertoss.checkout.service;

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
import com.rupertoss.checkout.model.Promotion;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@SqlGroup({
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
})
public class PromotionServiceTest {

	@Autowired
	PromotionService promotionService;
	
	@Test
	public void testGetByCode() {
		String code = "10%OFF";
		
		Promotion promotion = promotionService.getByCode(code);
		
		Assert.assertNotNull("failure - expected not to be null", promotion);
		Assert.assertEquals("failure - expected code attribute match", code, promotion.getCode());
	}
	
	@Test
	public void testGetByCodeNotFound() {
		String code = "X";
		
		Promotion promotion = promotionService.getByCode(code);
		
		Assert.assertNull("failure - expected to be null", promotion);
	}
}
