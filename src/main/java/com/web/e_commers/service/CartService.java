package com.web.e_commers.service;


import java.util.Set;

import com.web.e_commers.exception.ProductException;
import com.web.e_commers.model.Cart;
import com.web.e_commers.model.CartItem;
import com.web.e_commers.model.User;
import com.web.e_commers.request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public String addCartItem(Long userId,AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);
    public Set<CartItem> getCartItems(Long userId); 


 
}
