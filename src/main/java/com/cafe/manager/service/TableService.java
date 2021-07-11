package com.cafe.manager.service;

import com.cafe.manager.model.Table;
import java.util.List;

public interface TableService {

	/**
	 * Creates Table and persists in database
	 * @return persisted object
	 */
	Table createTable();

	/**
	 * Assigns waiter to table
	 * @param tableId id of the table
	 * @param waiterId id of the waiter
	 * @return Table model from database with updated data
	 */
	Table assignWaiter(Long tableId, Long waiterId);

	/**
	 * Gets all tables from database
	 * @return List of tables
	 */
	List<Table> getAllTables();

	/**
	 * Gets all tables assigned to waiter
	 * @param waiterId id of waiter in database
	 * @return List of tables
	 */
	List<Table> getAllTablesAssignedToWaiter(Long waiterId);
}
