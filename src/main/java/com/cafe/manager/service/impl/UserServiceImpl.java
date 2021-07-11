package com.cafe.manager.service.impl;

import static com.cafe.manager.mapper.UserMapper.entityToModel;
import static com.cafe.manager.mapper.UserMapper.modelToEntity;

import com.cafe.manager.entity.OrderEntity;
import com.cafe.manager.entity.TableEntity;
import com.cafe.manager.entity.UserEntity;
import com.cafe.manager.enums.Role;
import com.cafe.manager.mapper.UserMapper;
import com.cafe.manager.model.User;
import com.cafe.manager.repository.OrderRepository;
import com.cafe.manager.repository.TableRepository;
import com.cafe.manager.repository.UserRepository;
import com.cafe.manager.security.jwt.domain.UserPrincipal;
import com.cafe.manager.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;
	private final TableRepository tableRepository;
	private final OrderRepository orderRepository;

	@Override
	public User createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		UserEntity entity = modelToEntity(user);
		userRepository.save(entity);
		user.setId(entity.getId());

		return user;
	}

	@Override
	public Optional<User> getUserById(Long id) {
		return userRepository
			.findById(id)
			.map(UserMapper::entityToModel);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = entityToModel(userRepository.findByUsername(username));
		return new UserPrincipal(user);
	}

	@Override
	public void deleteUser(Long userId) {

		UserEntity userEntity = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);

		if (Role.ROLE_WAITER.name().equals(userEntity.getRole())) {
			List<TableEntity> tables = tableRepository.findByWaiter(userEntity);
			tables.forEach(tableEntity -> {
				tableEntity.setWaiter(null);
				tableRepository.save(tableEntity);
			});

			List<OrderEntity> orders = orderRepository.findByWaiter(userEntity);
			orders.forEach(orderEntity -> {
				orderEntity.setWaiter(null);
				orderRepository.save(orderEntity);
			});
		}

		userRepository.deleteById(userId);
	}

}
