package com.cafe.manager.service.impl;

import static com.cafe.manager.mapper.ProductMapper.modelToEntity;

import com.cafe.manager.entity.ProductEntity;
import com.cafe.manager.mapper.ProductMapper;
import com.cafe.manager.model.Product;
import com.cafe.manager.repository.ProductRepository;
import com.cafe.manager.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Override
	public Product createProduct(Product product) {
		ProductEntity productEntity = modelToEntity(product);
		productRepository.save(productEntity);
		product.setId(productEntity.getId());

		return product;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository
			.findAll()
			.stream()
			.map(ProductMapper::entityToModel)
			.collect(Collectors.toList());
	}
}

