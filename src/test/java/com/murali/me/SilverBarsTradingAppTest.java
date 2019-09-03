package com.murali.me;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.murali.me.model.OrderType;
import com.murali.me.model.dto.Order;
import com.murali.me.service.InvalidOrderException;
import com.murali.me.service.OrderManagementService;
import com.murali.me.service.OrderManagementServiceImpl;

@FixMethodOrder(MethodSorters.DEFAULT)
public class SilverBarsTradingAppTest {

	private static OrderManagementService oms;
	
	@BeforeClass
    public static void beforeClass() {
		oms = new OrderManagementServiceImpl();
    }
	
	@Test(expected = InvalidOrderException.class)
	public void testNullOrder() throws InvalidOrderException {
		oms.placeOrder(null);
	}
	
	@Test(expected = InvalidOrderException.class)
	public void testMissingUserIdOrder() throws InvalidOrderException {
		oms.placeOrder(
				Order.builder()
					.userId(null)
					.build()
				);
	}
	
	@Test(expected = InvalidOrderException.class)
	public void testInvalidQuantityOrder() throws InvalidOrderException {
		oms.placeOrder(
				Order.builder()
					.userId("user-1")
					.quantity(BigDecimal.valueOf(-1))
					.build()
				);
	}
	
	@Test(expected = InvalidOrderException.class)
	public void testInvalidPriceOrder() throws InvalidOrderException {
		oms.placeOrder(
				Order.builder()
					.userId("user-1")
					.quantity(BigDecimal.valueOf(100))
					.price(new BigDecimal("-100.35"))
					.build()
				);
	}
	
	@Test(expected = InvalidOrderException.class)
	public void testMissingBuySellIndOrder() throws InvalidOrderException {
		oms.placeOrder(
				Order.builder()
					.userId("user-1")
					.quantity(BigDecimal.valueOf(100))
					.price(new BigDecimal("100.35"))
					.build()
				);
	}
	
	@Test
	public void testValidOrder() throws InvalidOrderException {
		BigInteger orderId = oms.placeOrder(
				Order.builder()
					.userId("user-1")
					.quantity(BigDecimal.valueOf(100))
					.price(new BigDecimal("100.35"))
					.orderType(OrderType.BUY)
					.build()
				);
		assertEquals(BigInteger.ONE, orderId);
	}

}
