package com.iktpreobuka.schoolEDiary.entities.DTO;

import java.sql.Date;

public class StudentDTO extends UserDTO{
//	@JsonView(Views.Admin.class)
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date dateOfBirth;

	public StudentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
}
