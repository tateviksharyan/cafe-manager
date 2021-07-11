package com.cafe.manager.repository;

import com.cafe.manager.entity.ProductInOrderEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInOrderRepository extends JpaRepository<ProductInOrderEntity, UUID> {

}
