package com.iktpreobuka.schoolEDiary.entities.DTO;

import java.sql.Date;

public class StudentDTO extends UserDTO{
//	@JsonView(Views.Admin.class)

	private String schoolClass;
	private Integer year;

	public StudentDTO() {
		super();
	}	

	public String getSchoolClass() {
		return schoolClass;
	}

	public void setSchoolClass(String schoolClass) {
		this.schoolClass = schoolClass;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer schoolYear) {
		this.year = schoolYear;
	}
	
	
	
}
