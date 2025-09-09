package com.web.e_commers.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.e_commers.exception.ProductException;
import com.web.e_commers.exception.UserException;
import com.web.e_commers.model.Cart;
import com.web.e_commers.model.CartItem;
import com.web.e_commers.model.User;
import com.web.e_commers.repository.CartRepository;
import com.web.e_commers.request.AddItemRequest;
import com.web.e_commers.service.CartService;
import com.web.e_commers.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.respones.AppResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;





@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCartByJwt(@RequestHeader("Authorization") String jwt) throws UserException {
        // Remove "Bearer " prefix if present
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }

        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartRepository.findByUserId(user.getId());

        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/test") //check cart working or not
    public ResponseEntity<String> getCartTest() {
        return ResponseEntity.ok("Cart is working!");
    }
    
   
    
    @PutMapping("/add")
    @Operation(description = "add item to cart")
    public ResponseEntity<AppResponse> addItemCart(
            @RequestBody AddItemRequest req,
            @RequestHeader("Authorization") String jwt) 
            throws UserException, ProductException {

        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }

        User user = userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(), req);

        AppResponse res = new AppResponse();
        res.setMessage("item added to cart");
        res.setStatus(true);

        return ResponseEntity.ok(res); // ✅ clean and correct
    }
    @GetMapping("/items")
    public ResponseEntity<Set<CartItem>> getCartItems(@RequestHeader("Authorization") String jwt) throws UserException {
        if (jwt.startsWith("Bearer ")) jwt = jwt.substring(7);
        User user = userService.findUserProfileByJwt(jwt);
        Set<CartItem> items = cartService.getCartItems(user.getId());
        return ResponseEntity.ok(items);
    }


}
