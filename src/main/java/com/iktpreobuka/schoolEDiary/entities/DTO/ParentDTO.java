package com.iktpreobuka.schoolEDiary.entities.DTO;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ParentDTO extends UserDTO {
	@NotNull(message = "Child usrename must be provided.")
	List <String> childUsernames;

	public ParentDTO() {
		super();
	}

	public List<String> getChildUsernames() {
		return childUsernames;
	}

	public void setChildUsernames(List<String> childUsernames) {
		this.childUsernames = childUsernames;
	}

	
}
