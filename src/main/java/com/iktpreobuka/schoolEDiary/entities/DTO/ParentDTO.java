package com.iktpreobuka.schoolEDiary.entities.DTO;

import java.util.List;

public class ParentDTO extends UserDTO {
	List <String> childUsernames;

	public ParentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<String> getChildUsernames() {
		return childUsernames;
	}

	public void setChildUsernames(List<String> childUsernames) {
		this.childUsernames = childUsernames;
	}

	
}
