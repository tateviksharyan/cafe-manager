package com.cafe.manager.mapper;

import com.cafe.manager.entity.UserEntity;
import com.cafe.manager.enums.Role;
import com.cafe.manager.model.User;

public interface UserMapper {

	/**
	 * Maps the model to entity
	 * @param user model object
	 * @return entity object
	 */
	static UserEntity modelToEntity(User user) {
		if (user == null) {
			return null;
		}

		UserEntity userEntity = new UserEntity();

		userEntity.setId(user.getId());
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setEmail(user.getEmail());
		userEntity.setUsername(user.getUsername());
		userEntity.setPassword(user.getPassword());
		if (user.getRole() != null) {
			userEntity.setRole(user.getRole().name());
		}

		return userEntity;
	}

	/**
	 * Maps the entity to model
	 * @param userEntity entity object
	 * @return model object
	 */
	static User entityToModel(UserEntity userEntity) {
		if (userEntity == null) {
			return null;
		}

		User user = new User();

		user.setId(userEntity.getId());
		user.setFirstName(userEntity.getFirstName());
		user.setLastName(userEntity.getLastName());
		user.setEmail(userEntity.getEmail());
		user.setUsername(userEntity.getUsername());
		user.setPassword(userEntity.getPassword());
		if (userEntity.getRole() != null) {
			user.setRole(Enum.valueOf(Role.class, userEntity.getRole()));
		}

		return user;
	}
}
