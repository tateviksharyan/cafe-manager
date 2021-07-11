package com.cafe.manager.mapper;

import com.cafe.manager.entity.ProductEntity;
import com.cafe.manager.entity.ProductInOrderEntity;
import com.cafe.manager.model.ProductInOrder;

public interface ProductInOrderMapper {

	static ProductInOrderEntity modelToEntity(ProductInOrder productInOrder) {
		if (productInOrder == null) {
			return null;
		}

		ProductInOrderEntity productInOrderEntity = new ProductInOrderEntity();

		productInOrderEntity.setId(productInOrder.getId());
		productInOrderEntity.setCount(productInOrder.getCount());
		productInOrderEntity.setStatus(productInOrder.getStatus());

		return productInOrderEntity;
	}

	static ProductInOrder entityToModel(ProductInOrderEntity entity) {
		if (entity == null) {
			return null;
		}

		ProductInOrder productInOrder = new ProductInOrder();

		Long id = null;
		ProductEntity product = entity.getProduct();
		if (product != null) {
			id = product.getId();
		}

		productInOrder.setProductId(id);
		productInOrder.setId(entity.getId());
		productInOrder.setCount(entity.getCount());
		productInOrder.setStatus(entity.getStatus());

		return productInOrder;
	}
}
