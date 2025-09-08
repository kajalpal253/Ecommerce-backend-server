package com.web.e_commers.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.web.e_commers.exception.ProductException;
import com.web.e_commers.model.Product;
import com.web.e_commers.request.CreateProductRequest;

public interface ProductService {
	
	Product createProduct(CreateProductRequest req ) throws IOException;

	public String deleteProduct(Long productId)throws ProductException;
	public Product updateProduct(Long productId,Product req)throws ProductException;
	public Product findProductById(Long id) throws ProductException;
	public List<Product>findProductByCategory(String category);
	
	public Page<Product>getAllProduct(String category,List<String> colors,List<String>sizes,Integer minPrice, Integer maxPrice,
			Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize);

}
