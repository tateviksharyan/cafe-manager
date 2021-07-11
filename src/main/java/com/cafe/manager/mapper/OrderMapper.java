package com.cafe.manager.mapper;

import com.cafe.manager.entity.OrderEntity;
import com.cafe.manager.entity.ProductInOrderEntity;
import com.cafe.manager.model.Order;
import com.cafe.manager.model.ProductInOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface OrderMapper {

	static OrderEntity modelToEntity(Order order) {
		if (order == null) {
			return null;
		}

		OrderEntity orderEntity = new OrderEntity();

		orderEntity.setId(order.getId());
		List<ProductInOrderEntity> entities = null;
		final List<ProductInOrder> models = order.getProductInOrders();
		if (models != null) {
			entities = new ArrayList<>(models.size());
			for (ProductInOrder productInOrder : models) {
				ProductInOrderEntity productInOrderEntity = null;
				if (productInOrder != null) {
					productInOrderEntity = new ProductInOrderEntity();

					productInOrderEntity.setId(productInOrder.getId());
					productInOrderEntity.setCount(productInOrder.getCount());
					productInOrderEntity.setStatus(productInOrder.getStatus());
				}
				entities.add(productInOrderEntity);
			}
		}
		orderEntity.setProductInOrders(entities);
		orderEntity.setStatus(order.getStatus());

		return orderEntity;
	}

	static Order entityToModel(OrderEntity orderEntity) {
		if (orderEntity == null) {
			return null;
		}

		Order order = new Order();

		order.setId(orderEntity.getId());
		List<ProductInOrder> productInOrders = orderEntity.getProductInOrders().stream()
			.map(entity -> {
				ProductInOrder productInOrder = new ProductInOrder();
				productInOrder.setId(entity.getId());
				productInOrder.setCount(entity.getCount());
				productInOrder.setProductId(entity.getProduct().getId());
				productInOrder.setStatus(entity.getStatus());
				return productInOrder;
			})
			.collect(Collectors.toList());

		order.setProductInOrders(productInOrders);
		order.setStatus(orderEntity.getStatus());

		return order;
	}
}
