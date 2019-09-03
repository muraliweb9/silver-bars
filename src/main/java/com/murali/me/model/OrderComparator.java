package com.murali.me.model;

import java.util.Comparator;

import com.murali.me.model.dto.OrderType;

/**
 * For sorting BUY/SELL orders based on best price
 *  
 * @author murali
 *
 */
public class OrderComparator implements Comparator<OrderKey>{

	@Override
	public int compare(OrderKey o1, OrderKey o2) {
		int orderTypeCompare = o1.getOrderType().compareTo(o2.getOrderType());
		if (orderTypeCompare == 0) {
			if (o1.getOrderType() == OrderType.BUY) {
				return o2.getPrice().compareTo(o1.getPrice());
			}
			if (o1.getOrderType() == OrderType.SELL) {
				return o1.getPrice().compareTo(o2.getPrice());
			} 
		}
		return orderTypeCompare;
	}

}
