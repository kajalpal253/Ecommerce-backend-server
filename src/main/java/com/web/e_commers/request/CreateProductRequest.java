package com.web.e_commers.request;

import java.util.List;

import com.web.e_commers.model.Size;

public class CreateProductRequest {
	
	private String imageUrl;
    private String brand;
    private String title;
    private String color;
    private int discountedPrice;
    private int price;
    private int discountedPersent;
    private int quantity;
    private String topLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;
    private String description;

    private List<Size> size;


    

    // getters & setters
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public int getDiscountedPrice() { return discountedPrice; }
    public void setDiscountedPrice(int discountedPrice) { this.discountedPrice = discountedPrice; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public int getDiscountedPersent() { return discountedPersent; }
    public void setDiscountedPersent(int discountedPersent) { this.discountedPersent = discountedPersent; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getTopLevelCategory() { return topLevelCategory; }
    public void setTopLevelCategory(String topLevelCategory) { this.topLevelCategory = topLevelCategory; }
    public String getSecondLevelCategory() { return secondLevelCategory; }
    public void setSecondLevelCategory(String secondLevelCategory) { this.secondLevelCategory = secondLevelCategory; }
    public String getThirdLevelCategory() { return thirdLevelCategory; }
    public void setThirdLevelCategory(String thirdLevelCategory) { this.thirdLevelCategory = thirdLevelCategory; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Size> getSize() { return size; }
    public void setSize(List<Size> size) { this.size = size;
    
    }
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}//request class
