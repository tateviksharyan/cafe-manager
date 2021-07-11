package com.cafe.manager.mapper;

import com.cafe.manager.entity.TableEntity;
import com.cafe.manager.entity.UserEntity;
import com.cafe.manager.model.Table;
import java.util.ArrayList;
import java.util.List;

public interface TableMapper {

	static Table entityToModel(TableEntity entity) {
		if (entity == null) {
			return null;
		}

		Table cafeTable = new Table();

		UserEntity waiter = entity.getWaiter();

		Long id = null;
		if (waiter != null) {
			id = waiter.getId();
		}

		if (id != null) {
			cafeTable.setWaiterId(id);
		}

		if (entity.getId() != null) {
			cafeTable.setId(entity.getId());
		}

		return cafeTable;
	}

	static List<Table> entityListToModelList(List<TableEntity> tableEntities) {
		if (tableEntities == null) {
			return null;
		}

		List<Table> list = new ArrayList<>(tableEntities.size());
		for (TableEntity tableEntity : tableEntities) {
			list.add(entityToModel(tableEntity));
		}

		return list;
	}

	static TableEntity modelToEntity(Table model) {
		if (model == null) {
			return null;
		}

		TableEntity tableEntity = new TableEntity();

		UserEntity waiter = new UserEntity();

		if (model.getWaiterId() != null) {
			waiter.setId(model.getWaiterId());
		}

		tableEntity.setWaiter(waiter);

		if (model.getId() != null) {
			tableEntity.setId(model.getId());
		}

		return tableEntity;
	}
}
