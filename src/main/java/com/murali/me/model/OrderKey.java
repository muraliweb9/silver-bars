package com.murali.me.model;

import java.math.BigDecimal;

import com.murali.me.model.dto.Order;
import com.murali.me.model.dto.OrderType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderKey {

	private OrderType orderType;
	private BigDecimal price;
	
	public static OrderKey from(Order order) {
		return OrderKey
				.builder()
				.orderType(order.getOrderType())
				.price(order.getPrice())
				.build();
	}
}
