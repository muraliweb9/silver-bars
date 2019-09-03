package com.murali.me.model.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.murali.me.model.OrderType;

import lombok.Data;

/**
 * A POJO representing an order
 * 
 * @author murali
 *
 */
@Data
public class Order {
	
	BigInteger orderId;
	String userId;
	BigDecimal quantity;
	BigDecimal price;
	OrderType orderType;

}
