package com.murali.me.model.dto;

import java.math.BigDecimal;

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
	
	private String userId;
	private BigDecimal quantity;
	private BigDecimal price;
	private OrderType orderType;

}
