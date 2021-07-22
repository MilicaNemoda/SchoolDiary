package com.iktpreobuka.schoolEDiary.entities.DTO;

import java.util.ArrayList;
import java.util.List;

public class TeacherDTO extends UserDTO {

//	private List<String> classes = new ArrayList<String>();
	protected List<String> subjects = new ArrayList<String>();

//	public List<String> getClasses() {
//		return classes;
//	}
//
//	public void setClasses(List<String> classes) {
//		this.classes = classes;
//	}

	public List<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

}
