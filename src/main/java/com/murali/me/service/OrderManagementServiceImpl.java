package com.murali.me.service;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.murali.me.model.OrderComparator;
import com.murali.me.model.OrderKey;
import com.murali.me.model.dto.Order;

public class OrderManagementServiceImpl implements OrderManagementService {

	private BigInteger lastOrderId;
	private Map<BigInteger, Order> registeredOrders;
	
	public OrderManagementServiceImpl() {
		lastOrderId = ZERO;
		registeredOrders = new HashMap<>();
	}
	
	@Override
	public BigInteger placeOrder(Order order) throws InvalidOrderException {
		validateOrder(order);
		lastOrderId = lastOrderId.add(ONE);
		registeredOrders.put(lastOrderId, order);
		return lastOrderId;
	}
	
	@Override
	public void cancelOrder(BigInteger orderId) throws InvalidOrderException {
		if (orderId == null) {
			throw new InvalidOrderException("Null orderId");
		}
		if (!registeredOrders.containsKey(orderId)) {
			throw new InvalidOrderException(String.format("Invalid orderId: %s", orderId.toString()));
		}
		registeredOrders.remove(orderId);
	}
	
	@Override
	public Map<OrderKey, BigDecimal> summaryOfOrders() throws InvalidOrderException {
		Map<OrderKey, BigDecimal> orderSummary = new TreeMap<>(new OrderComparator());
		
		for (Order order : registeredOrders.values()) {
			OrderKey key = OrderKey.from(order);
			if (!orderSummary.containsKey(key)) {
				orderSummary.put(key, BigDecimal.ZERO);
			}
			orderSummary.put(key, orderSummary.get(key).add(order.getQuantity()));
		}
		
		return orderSummary;
	}
	
	private void validateOrder(Order order) throws InvalidOrderException {
		if(order == null) {
			throw new InvalidOrderException("Null order");
		}
		if (isBlank(order.getUserId())) {
			throw new InvalidOrderException("Null or missing userId");
		}
		if (order.getOrderType() == null) {
			throw new InvalidOrderException("Null or missing order type");
		}
		validateNumber(order.getPrice());
		validateNumber(order.getQuantity());
	}
	
	private void validateNumber(BigDecimal val) throws InvalidOrderException {
		if (val == null) {
			throw new InvalidOrderException("Null number value");
		}
		if (val.signum() != 1) {
			throw new InvalidOrderException("Non positive number value");
		}
	}

	
}
