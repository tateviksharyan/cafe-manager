package com.cafe.manager.service;

import com.cafe.manager.model.Product;
import java.util.List;

public interface ProductService {

	/**
	 * Creates product from given data
	 * @param product object to persist in database
	 * @return persisted object
	 */
	Product createProduct(Product product);

	/**
	 * Gets all products from database
	 * @return List of Products
	 */
	List<Product> getAllProducts();
}
