package com.iktpreobuka.schoolEDiary.entities.DTO;

import javax.validation.constraints.NotNull;

public class SchoolClassDTO {
	@NotNull(message = "School class name must be provided.")
	private String name;

	public SchoolClassDTO() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
