package com.cafe.manager;

import com.cafe.manager.config.AdminProperties;
import com.cafe.manager.enums.Role;
import com.cafe.manager.model.User;
import com.cafe.manager.service.UserService;
import java.util.Optional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(AdminProperties.class)
public class Application implements CommandLineRunner {

	private final UserService userService;
	private final AdminProperties adminProperties;

	public Application(UserService userService, AdminProperties adminProperties) {
		this.userService = userService;
		this.adminProperties = adminProperties;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Override
	public void run(String... args) {
		Optional<User> userById = userService.getUserById(1L);
		if (!userById.isPresent()) {
			User user = new User(
				adminProperties.getFirstName(),
				adminProperties.getLastName(),
				adminProperties.getEmail(),
				adminProperties.getUsername(),
				adminProperties.getPassword(),
				Role.ROLE_ADMIN
			);
			userService.createUser(user);
		}
	}
}
