package com.cafe.manager.controller;

import com.cafe.manager.model.Product;
import com.cafe.manager.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	/**
	 * Creates product from given data. Available only for manager
	 * @param product to persist in database
	 * @return Created product with id
	 */
	@PostMapping
	@PreAuthorize("hasRole(T(com.cafe.manager.enums.Role).ROLE_MANAGER)")
	public Product createProduct(@RequestBody Product product) {
		return productService.createProduct(product);
	}

	/**
	 * Gets all products. Available only for waiter
	 * @return List of products
	 */
	@GetMapping
	@PreAuthorize("hasRole(T(com.cafe.manager.enums.Role).ROLE_WAITER)")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}
}
