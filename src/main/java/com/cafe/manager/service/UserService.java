package com.cafe.manager.service;

import com.cafe.manager.model.User;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

	/**
	 * Creates and persists the user to database
	 * @param user object with all data
	 * @return persisted User object
	 */
	User createUser(User user);

	/**
	 * Gets user by given id
	 * @param id of User
	 * @return Optional with user object or empty Optional
	 */
	Optional<User> getUserById(Long id);

	/**
	 * Used in security configurations, loads user by username
	 * @param username to find the user in database
	 * @return UserPrincipal object created from user data
	 * @throws UsernameNotFoundException if no user found
	 */
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	/**
	 * Deletes user from database
	 * @param userId id of user that should be deleted
	 */
	void deleteUser(Long userId);
}
