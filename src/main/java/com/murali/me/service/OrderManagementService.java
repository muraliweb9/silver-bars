package com.murali.me.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import com.murali.me.model.OrderKey;
import com.murali.me.model.dto.Order;

/**
 * The external interface to the order management service
 * 
 * @author murali
 *
 */
public interface OrderManagementService {

	BigInteger placeOrder(Order order) throws InvalidOrderException;
	
	void cancelOrder(BigInteger orderId) throws InvalidOrderException;
	
	Map<OrderKey, BigDecimal> summaryOfOrders() throws InvalidOrderException;

	
}
