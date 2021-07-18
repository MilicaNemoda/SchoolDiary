package com.iktpreobuka.schoolEDiary.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})//cemu ovo sluzi?
public class AddressEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@JsonView(Views.Private.class)
	private Integer id;
	@Column(nullable = false)
//	@JsonView(Views.Private.class)
	private String street;
	@Column(nullable = false)
//	@JsonView(Views.Private.class)
	private String city;
	@Column(nullable = false)
//	@JsonView(Views.Private.class)
	private String country;
	@Version
	private Integer version;

	@JsonIgnore
	@OneToMany(mappedBy = "userAddress", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	private List<UserEntity> users = new ArrayList<>();

	public AddressEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

}
