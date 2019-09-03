package com.murali.me;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.murali.me.model.OrderKey;
import com.murali.me.model.dto.Order;
import com.murali.me.model.dto.OrderType;
import com.murali.me.service.InvalidOrderException;
import com.murali.me.service.OrderManagementService;
import com.murali.me.service.OrderManagementServiceImpl;

@TestMethodOrder(OrderAnnotation.class)
public class SilverBarsTradingAppTest {

	private static OrderManagementService oms;
	
	@BeforeAll
    public static void beforeClass() {
		oms = new OrderManagementServiceImpl();
    }
	
	@Test
	@org.junit.jupiter.api.Order(1)
	public void testNullOrder() throws InvalidOrderException {
		assertThrows(InvalidOrderException.class, () -> {oms.placeOrder(null);});
	}
	
	@Test
	@org.junit.jupiter.api.Order(2)
	public void testMissingUserIdOrder() throws InvalidOrderException {
		assertThrows(InvalidOrderException.class, () -> {
			oms.placeOrder(
					Order.builder()
						.userId(null)
						.build()
					);
			}
		);
	}
		
	@Test
	@org.junit.jupiter.api.Order(3)
	public void testInvalidQuantityOrder() throws InvalidOrderException {
		assertThrows(InvalidOrderException.class, () -> {
			oms.placeOrder(
					Order.builder()
						.userId("user-1")
						.quantity(BigDecimal.valueOf(-1))
						.build()
					);
			}
		);

	}
	
	@Test
	@org.junit.jupiter.api.Order(4)
	public void testInvalidPriceOrder() throws InvalidOrderException {
		assertThrows(InvalidOrderException.class, () -> {
			oms.placeOrder(
					Order.builder()
						.userId("user-1")
						.quantity(BigDecimal.valueOf(100))
						.price(new BigDecimal("-100.35"))
						.build()
					);
			}
		);

		

	}
	
	@Test
	@org.junit.jupiter.api.Order(5)
	public void testMissingBuySellIndOrder() throws InvalidOrderException {
		assertThrows(InvalidOrderException.class, () -> {
			oms.placeOrder(
					Order.builder()
						.userId("user-1")
						.quantity(BigDecimal.valueOf(100))
						.price(new BigDecimal("100.35"))
						.build()
					);
			}
		);


	}
	
	@Test
	@org.junit.jupiter.api.Order(6)	
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
	
	@Test
	@org.junit.jupiter.api.Order(7)
	public void testCancelNullOrder() throws InvalidOrderException {
		assertThrows(InvalidOrderException.class, () -> {oms.cancelOrder(null);});
	}
	
	@Test
	@org.junit.jupiter.api.Order(8)
	public void testCancelInvalidOrder() throws InvalidOrderException {
		assertThrows(InvalidOrderException.class, () -> {oms.cancelOrder(new BigInteger("2"));});
	}

	@Test
	@org.junit.jupiter.api.Order(9)
	public void testCancelValidOrder() throws InvalidOrderException {
		oms.cancelOrder(new BigInteger("1"));
	}
	
	@Test
	@org.junit.jupiter.api.Order(10)
	public void testCancelTwoOrders() throws InvalidOrderException {
		BigInteger firstOrderId = oms.placeOrder(
				Order.builder()
					.userId("user-1")
					.quantity(BigDecimal.valueOf(200))
					.price(new BigDecimal("100.35"))
					.orderType(OrderType.BUY)
					.build()
				);
		BigInteger secondOrderId = oms.placeOrder(
				Order.builder()
					.userId("user-1")
					.quantity(BigDecimal.valueOf(300))
					.price(new BigDecimal("100.65"))
					.orderType(OrderType.BUY)
					.build()
				);
		
		assertEquals(new BigInteger("2"), firstOrderId);
		assertEquals(new BigInteger("3"), secondOrderId);
		
		oms.cancelOrder(firstOrderId);
		oms.cancelOrder(secondOrderId);


	}
	
	/* Orders
	a) SELL: 3.5 kg for £306 [user1]
	b) SELL: 1.2 kg for £310 [user2]
	c) SELL: 1.5 kg for £307 [user3]
	d) SELL: 2.0 kg for £306 [user4]
	
	Our ‘Live Order Board’ should provide us the following summary information:
		- 5.5 kg for £306 // order a + order d
		- 1.5 kg for £307 // order c
		- 1.2 kg for £310 // order b
		
	*/
	@Test
	@org.junit.jupiter.api.Order(11)
	public void testOrderAggregationOrders() throws InvalidOrderException {
		BigInteger firstOrderId = oms.placeOrder(
				Order.builder()
					.userId("user-1")
					.quantity(new BigDecimal("3.5"))
					.price(new BigDecimal("306.00"))
					.orderType(OrderType.SELL)
					.build()
				);
		BigInteger secondOrderId = oms.placeOrder(
				Order.builder()
					.userId("user-2")
					.quantity(new BigDecimal("1.2"))
					.price(new BigDecimal("310.00"))
					.orderType(OrderType.SELL)
					.build()
				);
		
		BigInteger thirdOrderId = oms.placeOrder(
				Order.builder()
					.userId("user-3")
					.quantity(new BigDecimal("1.5"))
					.price(new BigDecimal("307.00"))
					.orderType(OrderType.SELL)
					.build()
				);
		BigInteger fourthOrderId = oms.placeOrder(
				Order.builder()
					.userId("user-4")
					.quantity(new BigDecimal("2.0"))
					.price(new BigDecimal("306.00"))
					.orderType(OrderType.SELL)
					.build()
				);
		
		assertEquals(new BigInteger("4"), firstOrderId);
		assertEquals(new BigInteger("5"), secondOrderId);
		assertEquals(new BigInteger("6"), thirdOrderId);
		assertEquals(new BigInteger("7"), fourthOrderId);
		
		Map<OrderKey, BigDecimal> orderSummary = oms.summaryOfOrders();

		assertEquals(3, orderSummary.size());
		
		//orderSummary.entrySet().stream().forEach(e -> System.out.println(e.getKey() + "=" + e.getValue()));

	}
	
	@Test
	@org.junit.jupiter.api.Order(12)
	public void testOrderAggregationAndSortingOrders() throws InvalidOrderException {
		BigInteger fifthOrderId = oms.placeOrder(
				Order.builder()
					.userId("user-5")
					.quantity(new BigDecimal("3.5"))
					.price(new BigDecimal("306.00"))
					.orderType(OrderType.BUY)
					.build()
				);
		BigInteger sixthOrderId = oms.placeOrder(
				Order.builder()
					.userId("user-6")
					.quantity(new BigDecimal("1.2"))
					.price(new BigDecimal("310.00"))
					.orderType(OrderType.BUY)
					.build()
				);
		
		BigInteger seventhOrderId = oms.placeOrder(
				Order.builder()
					.userId("user-7")
					.quantity(new BigDecimal("1.5"))
					.price(new BigDecimal("307.00"))
					.orderType(OrderType.BUY)
					.build()
				);
		BigInteger eigthOrderId = oms.placeOrder(
				Order.builder()
					.userId("user-8")
					.quantity(new BigDecimal("2.0"))
					.price(new BigDecimal("306.00"))
					.orderType(OrderType.BUY)
					.build()
				);
				
		Map<OrderKey, BigDecimal> orderSummary = oms.summaryOfOrders();

		assertEquals(6, orderSummary.size());

		/*
		OrderKey(orderType=BUY, price=310.00)=1.2
		OrderKey(orderType=BUY, price=307.00)=1.5
		OrderKey(orderType=BUY, price=306.00)=5.5
		OrderKey(orderType=SELL, price=306.00)=5.5
		OrderKey(orderType=SELL, price=307.00)=1.5
		OrderKey(orderType=SELL, price=310.00)=1.2
		*/
			

		OrderKey orderKey;
		BigDecimal orderQuantity;
		Entry<OrderKey, BigDecimal> entry;
		
		Iterator<Map.Entry<OrderKey,BigDecimal>> iter = orderSummary.entrySet().iterator();
		
		entry = iter.next();
		orderKey = entry.getKey();
		orderQuantity = entry.getValue();
		assertAggregation(orderKey, orderQuantity, OrderType.BUY, new BigDecimal("310.00"), new BigDecimal("1.2"));
		
		entry = iter.next();
		orderKey = entry.getKey();
		orderQuantity = entry.getValue();
		assertAggregation(orderKey, orderQuantity, OrderType.BUY, new BigDecimal("307.00"), new BigDecimal("1.5"));
		
		entry = iter.next();
		orderKey = entry.getKey();
		orderQuantity = entry.getValue();
		assertAggregation(orderKey, orderQuantity, OrderType.BUY, new BigDecimal("306.00"), new BigDecimal("5.5"));

		entry = iter.next();
		orderKey = entry.getKey();
		orderQuantity = entry.getValue();
		assertAggregation(orderKey, orderQuantity, OrderType.SELL, new BigDecimal("306.00"), new BigDecimal("5.5"));
		
		entry = iter.next();
		orderKey = entry.getKey();
		orderQuantity = entry.getValue();
		assertAggregation(orderKey, orderQuantity, OrderType.SELL, new BigDecimal("307.00"), new BigDecimal("1.5"));
		
		entry = iter.next();
		orderKey = entry.getKey();
		orderQuantity = entry.getValue();
		assertAggregation(orderKey, orderQuantity, OrderType.SELL, new BigDecimal("310.00"), new BigDecimal("1.2"));

		//orderSummary.entrySet().stream().forEach(e -> System.out.println(e.getKey() + "=" + e.getValue()));

	}

	private void assertAggregation(OrderKey key, BigDecimal val, OrderType expectedOrderType, 
			BigDecimal expectedPrice,
			BigDecimal expectedQuantity) {
		
		assertEquals(key.getOrderType(), expectedOrderType);
		assertEquals(key.getPrice(), expectedPrice);
		assertEquals(val, expectedQuantity);
		
		
	}
}
