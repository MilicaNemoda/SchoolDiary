
package com.iktpreobuka.schoolEDiary.entities;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class UserEntity {
//	@JsonView(Views.Public.class)
//	@JsonProperty("ID")
	private Integer id;
//	@JsonView(Views.Public.class)
	private String firstname;
//	@JsonView(Views.Public.class)
	private String lastname;
//	@JsonView(Views.Public.class)
	private String username;
//	@JsonView(Views.Admin.class)
	private String email;
//	@JsonIgnore
	private String password;
		
//	@JsonView(Views.Private.class)
//	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_address")
	protected AddressEntity address;
	
	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
