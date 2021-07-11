package com.cafe.manager.controller;

import com.cafe.manager.model.Table;
import com.cafe.manager.security.jwt.domain.UserPrincipal;
import com.cafe.manager.service.TableService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tables")
@RequiredArgsConstructor
public class TableController {
	private final TableService tableService;

	/**
	 * Creates table. Available only for manager
	 * @return Table object with created and persisted data
	 */
	@PostMapping
	@PreAuthorize("hasRole(T(com.cafe.manager.enums.Role).ROLE_MANAGER)")
	public Table createTable() {
		return tableService.createTable();
	}

	/**
	 * Gets all tables. Available only for manager
	 * @return List of tables
	 */
	@GetMapping
	@PreAuthorize("hasRole(T(com.cafe.manager.enums.Role).ROLE_MANAGER)")
	public List<Table> getAllTables() {
		return tableService.getAllTables();
	}

	/**
	 * Gets all tables assigned to waiter. Available only for waiter
	 * @param user object with waiter's data
	 * @return List of tables for waiter
	 */
	@GetMapping("/my")
	@PreAuthorize("hasRole(T(com.cafe.manager.enums.Role).ROLE_WAITER)")
	public List<Table> getAllTablesAssignedToWaiter(@AuthenticationPrincipal UserPrincipal user) {
		return tableService.getAllTablesAssignedToWaiter(user.getId());
	}

	/**
	 * Assigns waiter to table. Available only for manager
	 * @param tableId id of the table
	 * @param waiterId id of the waiter
	 * @return updated Table
	 */
	@PutMapping("/{tableId}/waiter/{waiterId}")
	@PreAuthorize("hasRole(T(com.cafe.manager.enums.Role).ROLE_MANAGER)")
	public Table assignWaiterToTable(@PathVariable Long tableId, @PathVariable Long waiterId) {
		return tableService.assignWaiter(tableId, waiterId);
	}
}
