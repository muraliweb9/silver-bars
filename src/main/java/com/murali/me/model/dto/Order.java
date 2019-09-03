package com.murali.me.model.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.murali.me.model.OrderType;

import lombok.Builder;
import lombok.Data;

/**
 * A POJO representing an order
 * 
 * @author murali
 *
 */
@Data
@Builder
public class Order {
	
	String userId;
	BigDecimal quantity;
	BigDecimal price;
	OrderType orderType;

}
