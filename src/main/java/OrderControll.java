

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;  // ✅ Use this!


import com.web.e_commers.exception.OrderException;
import com.web.e_commers.exception.UserException;
import com.web.e_commers.model.Address;
import com.web.e_commers.model.Order;
import com.web.e_commers.model.User;
import com.web.e_commers.service.OrderService;
import com.web.e_commers.service.UserService;


@RestController
@RequestMapping("/api/orders")
public class OrderControll {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private  UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<Order>createOrder(@RequestHeader("Authorization") String jwt,@RequestBody Address shippingAddress) throws UserException{
		
		if(jwt.startsWith("Bearer")) {
			jwt=jwt.substring(7);
		}
		
		User use = userService.findUserProfileByJwt(jwt);

		Order order =orderService.createOrder(use, shippingAddress);

		
		return new ResponseEntity<Order>(order,HttpStatus.CREATED);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Order>>userOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException{
		if(jwt.startsWith("Bearer")) {
			jwt = jwt.substring(7);
		}
		User user = userService.findUserProfileByJwt(jwt);
		List<Order>orders =orderService.usersOrderHistory(user.getId());
		return new ResponseEntity<>(orders,HttpStatus.CREATED);
		
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<Order> findOrderById(
	    @PathVariable("id") Long orderId,
	    @RequestHeader("Authorization") String jwt
	) throws OrderException, UserException {

	    if (jwt == null || !jwt.startsWith("Bearer ")) {
	        throw new UserException("Invalid or missing Authorization header");
	    }

	    jwt = jwt.substring(7);
	    User user = userService.findUserProfileByJwt(jwt);
	    Order order = orderService.findOrderById(orderId);

	    if (!order.getUser().getId().equals(user.getId())) {
	        throw new OrderException("Unauthorized access to order");
	    }

	    return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@DeleteMapping("/{orderId}")
	public void deleteOrder(@PathVariable("orderId") Long orderId,@RequestHeader("Authorization") String jwt) throws OrderException, UserException {
		if(jwt.startsWith("Bearer")) {
			jwt =jwt.substring(7);
		}
		User user =userService.findUserProfileByJwt(jwt);
		 orderService.deleteOrder(orderId);
	
	}
	
	
}
