package com.cafe.manager.model;

import com.cafe.manager.enums.OrderStatus;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Order {

	private Long id;
	private Table cafeTable;
	private List<ProductInOrder> productInOrders;
	private OrderStatus status;
}
