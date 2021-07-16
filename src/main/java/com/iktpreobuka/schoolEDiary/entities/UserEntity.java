
package com.iktpreobuka.schoolEDiary.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "User_Type")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@JsonView(Views.Public.class)
//	@JsonProperty("ID")
	private Integer id;
	@Column(nullable = false)
//	@JsonView(Views.Public.class)
	private String firstName;
	@Column(nullable = false)
//	@JsonView(Views.Public.class)
	private String lastName;
	@Column(nullable = false)
//	@JsonView(Views.Public.class)
	private String username;
	@Column(nullable = false)
//	@JsonView(Views.Admin.class)
	private String email;
	@Column(nullable = false)
//	@JsonIgnore
	private String password;

//	@JsonView(Views.Private.class)
//	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "userAddress")
	protected AddressEntity userAddress;

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

	public AddressEntity getAddress() {
		return userAddress;
	}

	public void setAddress(AddressEntity address) {
		this.userAddress = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
