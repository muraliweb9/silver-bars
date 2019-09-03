package com.murali.me.model;

import java.math.BigDecimal;

import com.murali.me.model.dto.Order;

import lombok.Data;

/**
 * Internal representation of the Order. This class holds the attributes used by the 
 * Order management system that are not exposed to the client
 * 
 * @author murali
 *
 */
@Data
public class OrderDetails {
	
	private BigDecimal orderId;
	private Order order;
	

}
