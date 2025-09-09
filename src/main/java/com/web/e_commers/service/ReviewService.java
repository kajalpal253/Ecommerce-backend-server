package com.web.e_commers.service;

import java.util.List;

import com.web.e_commers.exception.ProductException;
import com.web.e_commers.model.Review;
import com.web.e_commers.model.User;
import com.web.e_commers.request.ReviewRequest;

public interface ReviewService {

	
	public Review createReview(ReviewRequest req,User user)throws ProductException;
	public List<Review>getAllReview(Long productId);
}
//service