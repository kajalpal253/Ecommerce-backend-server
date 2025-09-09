package com.web.e_commers.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.web.e_commers.exception.ProductException;
import com.web.e_commers.model.Category;
import com.web.e_commers.model.Product;
import com.web.e_commers.model.Size;
import com.web.e_commers.repository.CategoryRepository;
import com.web.e_commers.repository.ProductRepository;
import com.web.e_commers.request.CreateProductRequest;

@Service
public class ProductServiceImplementation implements ProductService {
	private  ProductRepository productRepository;
	private UserService userService;
	private CategoryRepository categoryRepository;
	public ProductServiceImplementation(ProductRepository productRepository, UserService userService,CategoryRepository categoryRepository) {
		this.productRepository=productRepository;
		this.userService=userService;
		this.categoryRepository=categoryRepository;
		
	}

	@Override
	public Product createProduct(CreateProductRequest req) throws IOException {

	    // ------- CATEGORY CREATION LOGIC --------
	
		// Top Level
		List<Category> topList = categoryRepository.findAllByName(req.getTopLevelCategory());
		Category topLevel;
		if (topList.isEmpty()) {
		    topLevel = new Category();
		    topLevel.setName(req.getTopLevelCategory());
		    topLevel.setLevel(1);
		    topLevel = categoryRepository.save(topLevel);
		} else {
		    topLevel = topList.get(0); // agar multiple result ho, first use karo
		}

		// Second Level
		List<Category> secondList = categoryRepository.findAllByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());
		Category secondLevel;
		if (secondList.isEmpty()) {
		    secondLevel = new Category();
		    secondLevel.setName(req.getSecondLevelCategory());
		    secondLevel.setParentCategory(topLevel);
		    secondLevel.setLevel(2);
		    secondLevel = categoryRepository.save(secondLevel);
		} else {
		    secondLevel = secondList.get(0);
		}

		// Third Level
		List<Category> thirdList = categoryRepository.findAllByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());
		Category thirdLevel;
		if (thirdList.isEmpty()) {
		    thirdLevel = new Category();
		    thirdLevel.setName(req.getThirdLevelCategory());
		    thirdLevel.setParentCategory(secondLevel);
		    thirdLevel.setLevel(3);
		    thirdLevel = categoryRepository.save(thirdLevel);
		} else {
		    thirdLevel = thirdList.get(0);
		}
  

	    // ------- PRODUCT CREATION --------
		 Product product = new Product();
		    
		    product.setTitle(req.getTitle());
		    product.setColor(req.getColor());
		    product.setDescription(req.getDescription());
		    product.setDiscountedPrice(req.getDiscountedPrice());
		    product.setDiscountedPersent(req.getDiscountedPersent());

		    product.setBrand(req.getBrand());
		    product.setPrice(req.getPrice());
		    
		    // --- (Size mapping logic remains the same) ---
		    // ...
		    
		    product.setQuantity(req.getQuantity());
		    product.setCategory(thirdLevel);
		    product.setCreatedAt(LocalDateTime.now());
		    
		    // 💡 Add this line to set the image URL
		    product.setImageUrl(req.getImageUrl());

		    return productRepository.save(product);
	}


	@Override
	public String deleteProduct(Long productId) throws ProductException {
		Product product = findProductById(productId);
		product.getSize().clear();
		productRepository.delete(product);
		return "Product deleted Successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		Product product  = findProductById(productId);
		if(req.getQuantity()!= 0) {
			product.setQuantity(req.getQuantity());
			
		}
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		Optional<Product>opt =productRepository.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new ProductException("Product not found id -"+id);
		
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		
		Pageable pageble = PageRequest.of(pageNumber, pageSize);
		List<Product>products  = productRepository.filterProducts(category,minPrice, maxPrice, minDiscount, sort);
		if(!colors.isEmpty()) {
			products = products.stream().filter(p ->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
		}
		if(stock !=null) {
			if(stock.equals("in_stock")) {
				products =products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
				
			}
			else if(stock.equals("out_of_stock")) {
				products =products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
			}
		}
		int startIndex =(int)pageble.getOffset();
		int endIndex = Math.min(startIndex +pageble.getPageSize(),products.size());
		List<Product>pageContent = products.subList(startIndex, endIndex);
		Page<Product>filteredProducts = new PageImpl<>(pageContent,pageble,products.size());
		return filteredProducts;
	}

}//service
