package com.web.e_commers.request;

public class RatingRequest {
	private Long ProductId;
	private double rating;
	public Long getProductId() {
		return ProductId;
	}
	public void setProductId(Long productId) {
		ProductId = productId;
	}
	
	public void setRating(double rating) {
		this.rating = rating;
	}
	public double getRating() {
		// TODO Auto-generated method stub
		return rating;
	}

}
//request class