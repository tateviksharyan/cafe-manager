package com.cafe.manager.service.impl;

import static com.cafe.manager.mapper.TableMapper.entityListToModelList;
import static com.cafe.manager.mapper.TableMapper.entityToModel;

import com.cafe.manager.entity.TableEntity;
import com.cafe.manager.entity.UserEntity;
import com.cafe.manager.mapper.TableMapper;
import com.cafe.manager.model.Table;
import com.cafe.manager.repository.TableRepository;
import com.cafe.manager.repository.UserRepository;
import com.cafe.manager.service.TableService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

	private final TableRepository tableRepository;
	private final UserRepository userRepository;

	@Override
	public Table createTable() {
		TableEntity savedCafeTableEntity = tableRepository.save(new TableEntity());
		return entityToModel(savedCafeTableEntity);
	}

	@Override
	public Table assignWaiter(Long tableId, Long waiterId) {
		TableEntity cafeTableEntity = tableRepository.findById(tableId).orElseThrow(IllegalArgumentException::new);
		UserEntity userEntity = userRepository.findById(waiterId).orElseThrow(IllegalArgumentException::new);
		cafeTableEntity.setWaiter(userEntity);
		TableEntity updatedCafeTableEntity = tableRepository.save(cafeTableEntity);
		return entityToModel(updatedCafeTableEntity);
	}

	@Override
	public List<Table> getAllTables() {
		return tableRepository
			.findAll()
			.stream()
			.map(TableMapper::entityToModel)
			.collect(Collectors.toList());
	}

	@Override
	public List<Table> getAllTablesAssignedToWaiter(Long waiterId) {
		UserEntity userEntity = userRepository.findById(waiterId).orElseThrow(IllegalArgumentException::new);
		List<TableEntity> tables = tableRepository.findByWaiter(userEntity);
		return entityListToModelList(tables);
	}
}
