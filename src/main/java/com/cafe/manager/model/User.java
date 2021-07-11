package com.cafe.manager.model;

import com.cafe.manager.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class User {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	private String email;

	@NotNull
	private String username;

	@NotNull
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@NotNull
	private Role role;

	public User(@NotNull String firstName, @NotNull String lastName,
		@NotNull String email, @NotNull String username, @NotNull String password, @NotNull Role role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
	}
}
