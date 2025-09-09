package com.web.e_commers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.e_commers.model.OrderItem;

public interface OrderItemRepository  extends JpaRepository<OrderItem, Long>{
	

}
//repository