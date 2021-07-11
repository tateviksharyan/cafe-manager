package com.cafe.manager.entity;

import com.cafe.manager.enums.OrderStatus;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cafe_order")
public class OrderEntity extends BaseEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "table_id")
	private TableEntity table;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "waiter_id")
	private UserEntity waiter;

	@JoinColumn(name = "order_id")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductInOrderEntity> productInOrders;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
}
