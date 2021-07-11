package com.cafe.manager.service;

import com.cafe.manager.enums.OrderStatus;
import com.cafe.manager.enums.ProductInOrderStatus;
import com.cafe.manager.model.Order;
import com.cafe.manager.model.OrderRequest;
import com.cafe.manager.model.ProductInOrder;
import java.util.List;
import java.util.UUID;

public interface OrderService {
	Order createOrder(Long waiterId, OrderRequest order);

	List<Order> getAllOrders();

	Order editOrderStatus(Long waiterId, Long orderId, OrderStatus status);

	ProductInOrder editProductsInOrder(Long waiterId, UUID productsInOrderId, ProductInOrderStatus status, Integer count);

	Order addProductsToOrder(Long id, OrderRequest order);
}
