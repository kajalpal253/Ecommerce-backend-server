package com.web.e_commers.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.web.e_commers.exception.ProductException;
import com.web.e_commers.model.Product;
import com.web.e_commers.model.Rating;
import com.web.e_commers.model.User;
import com.web.e_commers.repository.RatingRepository;
import com.web.e_commers.request.RatingRequest;
@Service
public class RatingServiceImplementation implements RatingService {
	private RatingRepository ratingRepository;
	private ProductService productService;
	
	

	

	public RatingServiceImplementation(RatingRepository ratingRepository, ProductService productService) {
		this.ratingRepository = ratingRepository;
		this.productService = productService;
	}

	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {
     Product product = productService.findProductById(req.getProductId());
     Rating rating = new Rating();
     rating.setProduct(product);
     rating.setUser(user);
     rating.setRating(req.getRating());
     rating.setCreatedAt(LocalDateTime.now());
     return ratingRepository.save(rating);

	}

	@Override
	public List<Rating> getProductRating(Long productId) {
		
		return ratingRepository.getAllProductsRating(productId);
	}

}
