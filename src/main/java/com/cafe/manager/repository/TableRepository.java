package com.cafe.manager.repository;

import com.cafe.manager.entity.TableEntity;
import com.cafe.manager.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long> {
	List<TableEntity> findByWaiter(UserEntity userEntity);
}
