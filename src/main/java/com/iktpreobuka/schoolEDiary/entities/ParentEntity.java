package com.iktpreobuka.schoolEDiary.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

public class ParentEntity extends UserEntity {
//	@JsonView(Views.Private.class)
//	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "Parent_Student", joinColumns = {
			@JoinColumn(name = "Parent_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "Student_id", nullable = false, updatable = false) })
	protected Set<StudentEntity> student = new HashSet<StudentEntity>();

	public ParentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Set<StudentEntity> getStudent() {
		return student;
	}

	public void setStudent(Set<StudentEntity> student) {
		this.student = student;
	}

}
