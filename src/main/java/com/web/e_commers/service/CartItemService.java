package com.web.e_commers.service;

import com.web.e_commers.exception.CartItemException;
import com.web.e_commers.exception.UserException;
import com.web.e_commers.model.Cart;
import com.web.e_commers.model.CartItem;
import com.web.e_commers.model.Product;

public interface CartItemService {

	public CartItem createCartItem(CartItem  cartItem);
	
	 public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) 
	            throws CartItemException, UserException;	
	public CartItem isCartItemExist(Cart cart,Product product,String size, Long userId);
	
	public  void removeCartItem(Long userId,Long cartItemId) throws CartItemException,UserException;
	
	public CartItem findCartItemById(Long cartItemId)throws CartItemException;

	

}

	
