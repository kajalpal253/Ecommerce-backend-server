package com.web.e_commers.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.web.e_commers.exception.OrderException;
import com.web.e_commers.model.Address;
import com.web.e_commers.model.Cart;
import com.web.e_commers.model.CartItem;
import com.web.e_commers.model.Order;
import com.web.e_commers.model.OrderItem;
import com.web.e_commers.model.User;
import com.web.e_commers.repository.AddressRepository;
import com.web.e_commers.repository.OrderItemRepository;
import com.web.e_commers.repository.OrderRepository;
import com.web.e_commers.repository.UserRepository;

@Service
public class OrderServiceImplementation implements  OrderService {
	
	
	private OrderRepository orderRepository;
	private UserRepository  userRepository;
	private CartService cartService;
	private AddressRepository addressRepository;
	private OrderItemService orderItemService;
	private OrderItemRepository orderItemRepository;
	
	

	public OrderServiceImplementation(OrderRepository orderRepository, UserRepository userRepository,
			CartService cartService, AddressRepository addressRepository,
			OrderItemService orderItemService, OrderItemRepository orderItemRepository) {
		
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.cartService = cartService;
		this.addressRepository = addressRepository;
		this.orderItemService = orderItemService;
		this.orderItemRepository = orderItemRepository;
	}

	@Override
	public Order createOrder(User user, Address shippingAddress) {
		
		shippingAddress.setUser(user);
        Address savedAddress = addressRepository.save(shippingAddress);
        user.getAddress().add(savedAddress);
        userRepository.save(user);
		 Cart cart = cartService.findUserCart(user.getId());
		 List<OrderItem>orderItem= new ArrayList<>();
		  for(CartItem item: cart.getCartItems()) {
			  OrderItem orderItem1 = new OrderItem();
			  
			  orderItem1.setPrice(item.getPrice());
			  orderItem1.setProduct(item.getProduct());
			  orderItem1.setQuantity(item.getQuantity());
			  orderItem1.setSize(item.getSize());
			  orderItem1.setUserId(item.getUserId());
			  orderItem1.setDiscountedPrice(item.getDiscountedPrice());
			  
			  OrderItem createOrderItem = orderItemRepository.save(orderItem1);
			  orderItem.add(createOrderItem);
			  
			  
			  
		  }
		  
		  
		  
		  Order createOrder = new Order();
		  createOrder.setUser(user);
		  createOrder.setOrderItems(orderItem);
		  createOrder.setTotalPrice(cart.getTotalPrice());
		  createOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		  createOrder.setDiscount(cart.getDiscount());
		  createOrder.setTotalItem(cart.getTotalItem());
		  createOrder.setShippingAddress(savedAddress);
		  createOrder.setOrderDate(LocalDateTime.now());
		  createOrder.setOrderStatus("PENDING");
		  createOrder.getPaymentDetails().setStatus("PENDING");
		  createOrder.setCreateAt(LocalDateTime.now());
		  
		  Order savedOrder = orderRepository.save(createOrder);
		  for(OrderItem item : orderItem) {
			  item.setOrder(savedOrder);
			  orderItemRepository.save(item);
			  
		  }
		  return savedOrder;
		  
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order>opt =orderRepository.findById(orderId);
		if(opt.isPresent()) {
			return opt.get();
			
		}
		throw new OrderException("order not exist with id" +orderId);
		
	}

	@Override
	public List<Order> usersOrderHistory(Long userId) {
		List<Order>orders =orderRepository.getUsersOrders(userId);
		return orders;
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		
		return orderRepository.save(order);
	}

	@Override
	public Order shippeddOrder(Long orderId) throws OrderException {
		Order order =findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return orderRepository.save(order);
	}

	@Override
	public Order delivereOrder(Long orderId) throws OrderException {
		Order order =findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		return orderRepository.save(order);	}

	@Override
	public Order cancleOderOrder(Long orderId) throws OrderException {
		Order order =findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
	 Order	order=  findOrderById(orderId);
	 orderRepository.deleteById(orderId);
		
	}

}
