package com.web.e_commers.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.web.e_commers.exception.CartItemException;
import com.web.e_commers.exception.UserException;
import com.web.e_commers.model.Cart;
import com.web.e_commers.model.CartItem;
import com.web.e_commers.model.Product;
import com.web.e_commers.model.User;
import com.web.e_commers.repository.CartItemRepository;
import com.web.e_commers.repository.CartRepository;


@Service
public class CartItemServiceImplementation implements CartItemService {

    private UserService userService;
    private CartRepository cartRepository;

       private CartItemRepository cartItemRepository;

   
    public CartItemServiceImplementation(UserService userService, CartRepository cartRepository,
             CartItemRepository cartItemRepository ) {

        this.userService = userService;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
        CartItem createdCartItem = cartItemRepository.save(cartItem);
        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {

        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUserId());

        if (user.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
        }

       return  cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        CartItem cartItem= cartItemRepository.isCartItemExist(cart, product, size, userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) {
        Cart cart = cartRepository.findByUserId(userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId).get(); // assuming it exists
        
        // Remove the item from the cart's item list
        Set<CartItem> items = cart.getCartItems();
        items.removeIf(item -> item.getId().equals(cartItemId));
        
        // Delete from DB
        cartItemRepository.deleteById(cartItemId);

        // Recalculate cart totals
        int totalItems = 0;
        int totalPrice = 0;
        int totalDiscountedPrice = 0;

        for (CartItem item : items) {
            totalItems += item.getQuantity();
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
            totalDiscountedPrice += item.getProduct().getDiscountedPrice() * item.getQuantity();
        }

        cart.setCartItems(items);
        cart.setTotalitem(totalItems);
        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setDiscount(totalPrice - totalDiscountedPrice); 


        cartRepository.save(cart);
    }



    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);

        if (opt.isPresent()) {
            return opt.get();
        }
        throw new CartItemException("CartItem not found with id: " + cartItemId);
    }
    


}
