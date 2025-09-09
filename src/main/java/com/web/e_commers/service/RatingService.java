package com.web.e_commers.service;

import java.util.List;


import com.web.e_commers.exception.ProductException;
import com.web.e_commers.model.Rating;
import com.web.e_commers.model.User;
import com.web.e_commers.request.RatingRequest;


public interface RatingService {
	public Rating createRating(RatingRequest req, User user) throws ProductException;
	public List<Rating>getProductRating(Long productId);
	

}
//service