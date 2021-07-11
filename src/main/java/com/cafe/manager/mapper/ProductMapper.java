package com.cafe.manager.mapper;

import com.cafe.manager.entity.ProductEntity;
import com.cafe.manager.model.Product;

public interface ProductMapper {

	static ProductEntity modelToEntity(Product model) {
		if (model == null) {
			return null;
		}

		ProductEntity productEntity = new ProductEntity();

		productEntity.setId(model.getId());
		productEntity.setName(model.getName());
		productEntity.setPrice(model.getPrice());

		return productEntity;
	}

	static Product entityToModel(ProductEntity entity) {
		if (entity == null) {
			return null;
		}

		Product product = new Product();

		product.setId(entity.getId());
		product.setName(entity.getName());
		product.setPrice(entity.getPrice());

		return product;
	}
}
