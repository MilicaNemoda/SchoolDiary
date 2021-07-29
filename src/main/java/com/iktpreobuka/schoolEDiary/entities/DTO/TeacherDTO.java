package com.iktpreobuka.schoolEDiary.entities.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class TeacherDTO extends UserDTO {
	@NotNull(message = "School class must be provided.")
	private List<String> classes = new ArrayList<String>();
	@NotNull(message = "School subject must be provided.")
	protected List<String> subjects = new ArrayList<String>();
	
//	@Override
//	public String toString() {
//		return "TeacherDTO [classes=" + classes + ", subjects=" + subjects + "]";
//	}

	public List<String> getClasses() {
		return classes;
	}

	public void setClasses(List<String> classes) {
		this.classes = classes;
	}

	public List<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

}
