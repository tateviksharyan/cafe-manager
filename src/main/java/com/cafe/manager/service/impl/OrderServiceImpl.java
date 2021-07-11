package com.cafe.manager.service.impl;

import static com.cafe.manager.mapper.ProductInOrderMapper.entityToModel;
import static com.cafe.manager.mapper.ProductInOrderMapper.modelToEntity;

import com.cafe.manager.entity.OrderEntity;
import com.cafe.manager.entity.ProductEntity;
import com.cafe.manager.entity.ProductInOrderEntity;
import com.cafe.manager.entity.TableEntity;
import com.cafe.manager.entity.UserEntity;
import com.cafe.manager.enums.OrderStatus;
import com.cafe.manager.enums.ProductInOrderStatus;
import com.cafe.manager.exceptions.ForbiddenException;
import com.cafe.manager.exceptions.NoOpenOrdersException;
import com.cafe.manager.exceptions.OpenedOrderExistingException;
import com.cafe.manager.mapper.OrderMapper;
import com.cafe.manager.model.Order;
import com.cafe.manager.model.OrderRequest;
import com.cafe.manager.model.ProductInOrder;
import com.cafe.manager.repository.OrderRepository;
import com.cafe.manager.repository.ProductInOrderRepository;
import com.cafe.manager.repository.ProductRepository;
import com.cafe.manager.repository.TableRepository;
import com.cafe.manager.repository.UserRepository;
import com.cafe.manager.service.OrderService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ProductInOrderRepository productsInOrderRepository;
	private final ProductRepository productRepository;
	private final TableRepository tableRepository;

	@Override
	public Order createOrder(Long waiterId, OrderRequest order) {
		TableEntity tableEntity = tableRepository.findById(order.getTableId()).orElseThrow(IllegalArgumentException::new);
		UserEntity waiterEntity = userRepository.findById(waiterId).orElseThrow(IllegalArgumentException::new);

		if (tableEntity.getWaiter() == null || !waiterId.equals(tableEntity.getWaiter().getId())) {
			throw new ForbiddenException();
		}

		List<OrderEntity> openOrders = orderRepository.findOrdersByTableAndStatus(tableEntity, OrderStatus.OPEN);

		if (!openOrders.isEmpty()) {
			final String err = String.format("Table %d has open orders", order.getTableId());
			log.error(err);
			throw new OpenedOrderExistingException();
		}

		OrderEntity orderEntity = new OrderEntity();

		orderEntity.setWaiter(waiterEntity);
		orderEntity.setStatus(OrderStatus.OPEN);
		orderEntity.setTable(tableEntity);

		List<ProductInOrderEntity> products = toEntityList(order.getProductsInOrder());
		orderEntity.setProductInOrders(products);

		OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
		return OrderMapper.entityToModel(savedOrderEntity);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository
			.findAll()
			.stream()
			.map(OrderMapper::entityToModel)
			.collect(Collectors.toList());
	}

	@Override
	public Order editOrderStatus(Long waiterId, Long orderId, OrderStatus status) {
		OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
		if (!waiterId.equals(orderEntity.getTable().getWaiter().getId())) {
			throw new ForbiddenException();
		}

		orderEntity.setStatus(status);
		OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
		return OrderMapper.entityToModel(savedOrderEntity);
	}

	@Override
	public ProductInOrder editProductsInOrder(Long waiterId, UUID productsInOrderId,
		ProductInOrderStatus status, Integer count) {
		ProductInOrderEntity productInOrderEntity = productsInOrderRepository
			.findById(productsInOrderId).orElseThrow(IllegalArgumentException::new);

		if (count != null) {
			productInOrderEntity.setCount(count);
		}

		if (status != null) {
			productInOrderEntity.setStatus(status);
		}

		ProductInOrderEntity savedEntity = productsInOrderRepository.save(productInOrderEntity);
		return entityToModel(savedEntity);
	}

	@Override
	public Order addProductsToOrder(Long waiterId, OrderRequest order) {
		TableEntity tableEntity = tableRepository.findById(order.getTableId()).orElseThrow(IllegalArgumentException::new);
		List<OrderEntity> openOrders = orderRepository.findOrdersByTableAndStatus(tableEntity, OrderStatus.OPEN);

		if (openOrders.isEmpty()) {
			final String err = String.format("Table %d has no open orders", order.getTableId());
			log.error(err);
			throw new NoOpenOrdersException();
		}

		OrderEntity orderEntity = openOrders.get(0);
		if (!waiterId.equals(orderEntity.getTable().getWaiter().getId())) {
			throw new ForbiddenException();
		}

		List<ProductInOrderEntity> products = toEntityList(order.getProductsInOrder());
		orderEntity.getProductInOrders().addAll(products);

		OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

		return OrderMapper.entityToModel(savedOrderEntity);
	}

	private List<ProductInOrderEntity> toEntityList(List<ProductInOrder> productsInOrder) {
		return productsInOrder.stream()
			.map(productInOrder -> {
				ProductInOrderEntity productInOrderEntity = modelToEntity(productInOrder);
				ProductEntity productEntity = productRepository.findById(productInOrder
					.getProductId()).orElseThrow(IllegalArgumentException::new);
				productInOrderEntity.setProduct(productEntity);
				return productInOrderEntity;
			})
			.collect(Collectors.toList());
	}
}
