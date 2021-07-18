package com.iktpreobuka.schoolEDiary.entities.DTO;

import java.sql.Date;

public class StudentDTO extends UserDTO{
//	@JsonView(Views.Admin.class)
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
//	private Date dateOfBirth;
//	private String parentUsername;
	private String schoolClass;
	private Integer year;

	public StudentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

//	public String getParentUsername() {
//		return parentUsername;
//	}
//
//	public void setParentUsername(String parentUsername) {
//		this.parentUsername = parentUsername;
//	}

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
