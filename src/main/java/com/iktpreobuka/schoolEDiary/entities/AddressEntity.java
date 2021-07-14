package com.iktpreobuka.schoolEDiary.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

public class AddressEntity {
//	@JsonView(Views.Private.class)
	private Integer id;
//	@JsonView(Views.Private.class)
	private String street;
//	@JsonView(Views.Private.class)
	private String city;
//	@JsonView(Views.Private.class)
	private String country;
	private Integer version;

	@OneToMany(mappedBy = "user_address", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	private List<UserEntity> users = new ArrayList<>();
}
