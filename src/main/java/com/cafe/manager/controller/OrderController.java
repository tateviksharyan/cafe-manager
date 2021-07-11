package com.cafe.manager.controller;

import com.cafe.manager.enums.OrderStatus;
import com.cafe.manager.enums.ProductInOrderStatus;
import com.cafe.manager.model.Order;
import com.cafe.manager.model.OrderRequest;
import com.cafe.manager.model.ProductInOrder;
import com.cafe.manager.security.jwt.domain.UserPrincipal;
import com.cafe.manager.service.OrderService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	/**
	 * Creates Order. Available only for waiter
	 * @param order to persist in database
	 * @param principal to check if this order is users order.
	 * @return Created order with id
	 */
	@PostMapping
	@PreAuthorize("hasRole(T(com.cafe.manager.enums.Role).ROLE_WAITER)")
	public Order createOrder(@AuthenticationPrincipal UserPrincipal principal,
		@RequestBody OrderRequest order) {
		return orderService.createOrder(principal.getId(), order);
	}

	/**
	 * Gets all orders
	 * @return List of orders
	 */
	@GetMapping
	@PreAuthorize("hasRole(T(com.cafe.manager.enums.Role).ROLE_MANAGER)")
	public List<Order> getAllOrders() {
		return orderService.getAllOrders();
	}

	/**
	 * Adds product to order. Available only for waiter
	 * @param order to persist in database
	 * @param principal to check if this order is users order.
	 * @return Created order with id
	 */
	@PostMapping("/products")
	@PreAuthorize("hasRole(T(com.cafe.manager.enums.Role).ROLE_WAITER)")
	public Order addProductToOrder(@AuthenticationPrincipal UserPrincipal principal,
		@RequestBody OrderRequest order) {
		return orderService.addProductsToOrder(principal.getId(), order);
	}

	/**
	 * Edits order status. Available only for waiter
	 * @param orderId which must be edited
	 * @param status new status for edit
	 * @param principal to check if this order is users order
	 * @return updated order object
	 */
	@PatchMapping("/{orderId}")
	@PreAuthorize("hasRole(T(com.cafe.manager.enums.Role).ROLE_WAITER)")
	public Order editOrderStatus(@AuthenticationPrincipal UserPrincipal principal,
		@PathVariable Long orderId, @RequestParam OrderStatus status) {
		return orderService.editOrderStatus(principal.getId(), orderId, status);
	}

	/**
	 * Edits products in order. Available only for waiter
	 * @param productRowId which must be updated
	 * @param principal to check if this order is users order
	 * @param status new status for edit
	 * @param count updated count
	 * @return updated productsInOrder object
	 */
	@PatchMapping("/products/{productRowId}")
	@PreAuthorize("hasRole(T(com.cafe.manager.enums.Role).ROLE_WAITER)")
	public ProductInOrder editProductsInOrder(@AuthenticationPrincipal UserPrincipal principal,
		@PathVariable UUID productRowId,
		@RequestParam(required = false) ProductInOrderStatus status,
		@RequestParam(required = false) Integer count) {
		return orderService.editProductsInOrder(principal.getId(), productRowId, status, count);
	}

}
