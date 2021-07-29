package com.iktpreobuka.schoolEDiary.entities.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SchoolClassDTO {
	@NotNull(message = "School class name must be provided.")
	@Pattern(regexp ="^[1-8]{1}/"+"[1-4]{1}$", message = "School class name is not valid.")
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
