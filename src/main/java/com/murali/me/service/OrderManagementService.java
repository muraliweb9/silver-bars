package com.murali.me.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import com.murali.me.model.OrderKey;
import com.murali.me.model.dto.Order;
import com.murali.me.model.dto.OrderType;

/**
 * The external interface to the order management service
 * 
 * @author murali
 *
 */
public interface OrderManagementService {
	
	/**
	 * Place an order with the order management system
	 * 
	 * @param order
	 * @return the registered order ID in the system
	 * @throws InvalidOrderException if order is invalid
	 */
	BigInteger placeOrder(Order order) throws InvalidOrderException;
	
	/**
	 * Cancel an order that was placed
	 * 
	 * @param orderId the order ID that was returned when the order was placed
	 * @throws InvalidOrderException if order ID is invalid
	 */
	void cancelOrder(BigInteger orderId) throws InvalidOrderException;
	
	/**
	 * Returns a sorted summary of orders.
	 * 
	 * BUY orders are listed high to low
	 * SELL orders are listed low to high
	 * @return The registered orders
	 * @throws InvalidOrderException
	 */
	Map<OrderKey, BigDecimal> summaryOfOrders() throws InvalidOrderException;

	/**
	 * Returns the sorted summary of orders filtered by order type
	 * 
	 * @param orderType @see com.murali.me.model.dto.OrderType
	 * @return
	 * @throws InvalidOrderException
	 */
	Map<OrderKey, BigDecimal> summaryOfOrders(OrderType orderType) throws InvalidOrderException;
	
}
