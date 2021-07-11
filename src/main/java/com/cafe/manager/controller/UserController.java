package com.cafe.manager.controller;

import com.cafe.manager.model.User;
import com.cafe.manager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	/**
	 * Creates user by given data. Available only for admin and manager
	 * @param user object with data passed in request body
	 * @return persisted User object
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyRole(T(com.cafe.manager.enums.Role).ROLE_ADMIN, T(com.cafe.manager.enums.Role).ROLE_MANAGER)")
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

}
