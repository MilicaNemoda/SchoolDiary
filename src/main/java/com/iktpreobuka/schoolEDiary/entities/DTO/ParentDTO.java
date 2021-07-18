package com.iktpreobuka.schoolEDiary.entities.DTO;

import java.util.List;

public class ParentDTO extends UserDTO {
	List <String> childUsername;

	public ParentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<String> getChildUsername() {
		return childUsername;
	}

	public void setChildUsername(List<String> childUsername) {
		this.childUsername = childUsername;
	}

	
}
