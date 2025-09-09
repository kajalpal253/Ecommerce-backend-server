package com.web.e_commers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.e_commers.model.OrderItem;

import com.web.e_commers.repository.OrderItemRepository;


@Service
public class OrderItemServiceImplementation  implements OrderItemService{
	
	@Autowired
	private OrderItemRepository orderItemRepository;

	
	
	


	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		
		
		return orderItemRepository.save(orderItem);
	}

}//service
