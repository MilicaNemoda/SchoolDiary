package com.iktpreobuka.schoolEDiary.entities.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.iktpreobuka.schoolEDiary.entities.RoleEntity;


public class UserDTO {
	private Integer id;
	@NotNull(message = "First name must be provided.")
	private String firstName;
	@NotNull(message = "Last name must be provided.")
	private String lastName;
	@NotNull(message = "Username must be provided.")
	private String username;
	@NotNull(message = "Email must be provided.")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
	message="Email is not valid.")
	private String email;
	@NotNull(message = "Password must be provided.")
	private String password;
	@NotNull(message = "Confirm password must be provided.")
	private String repeatedPassword;
	@NotNull(message = "Role must be provided.")
	private RoleEntity role;
	private String token;

	public UserDTO() {
		super();
		
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", email=" + email + ", role=" + role + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}	

}
