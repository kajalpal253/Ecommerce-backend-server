package com.web.e_commers.controller;

import com.web.e_commers.exception.CartItemException;
import com.web.e_commers.exception.UserException;
import com.web.e_commers.model.CartItem;
import com.web.e_commers.model.User;
import com.web.e_commers.repository.CartItemRepository;
import com.web.e_commers.service.CartItemService;
import com.web.e_commers.service.CartService;
import com.web.e_commers.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.respones.AppResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    @Autowired
    private CartService cartService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CartItemService cartItemService;
    
    @Autowired
    private CartItemRepository cartItemRepository;


   
  
    
    @DeleteMapping("/cart_items/{cartItemId}")
    @Operation(description = "Remove cartItem from Cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    public ResponseEntity<AppResponse> deleteCartItem(
            @PathVariable("cartItemId") Long cartItemId,
            @RequestHeader("Authorization") String jwt
    ) throws CartItemException, UserException {
    	 if (jwt.startsWith("Bearer ")) {
             jwt = jwt.substring(7);
         }
        
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        AppResponse res = new AppResponse();
        res.setMessage("Item deleted from cart");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    
    @PutMapping("/{cartItemId}")
    @Operation(description ="Update Item to Cart")
    public ResponseEntity<CartItem> updateCartItem(
    		@RequestBody CartItem cartItem,
    		@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws UserException,CartItemException{
    	 if (jwt.startsWith("Bearer ")) {
             jwt = jwt.substring(7);
         }
    	
    	User user = userService.findUserProfileByJwt(jwt);
    	CartItem updateCartItem =cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
    	return new ResponseEntity<>(updateCartItem,HttpStatus.OK);
    	
    }
    


}
