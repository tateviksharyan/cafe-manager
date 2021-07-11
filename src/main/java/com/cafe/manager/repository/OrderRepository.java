package com.cafe.manager.repository;

import com.cafe.manager.entity.OrderEntity;
import com.cafe.manager.entity.TableEntity;
import com.cafe.manager.entity.UserEntity;
import com.cafe.manager.enums.OrderStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	List<OrderEntity> findOrdersByTableAndStatus(TableEntity cafeTableEntity, OrderStatus status);

	List<OrderEntity> findByWaiter(UserEntity userEntity);
}
