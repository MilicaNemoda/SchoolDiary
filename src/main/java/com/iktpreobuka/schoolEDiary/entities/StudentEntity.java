package com.iktpreobuka.schoolEDiary.entities;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class StudentEntity extends UserEntity {
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	@JoinColumn(name = "studentSchoolClass")
	private SchoolClassEntity studentSchoolClass;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "schoolYearStudent")
	private SchoolYearEntity schoolYearStudent;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "Parent_Student", joinColumns = {
			@JoinColumn(name = "Student_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "Parent_id", nullable = false, updatable = false) })
	protected Set<ParentEntity> parent = new HashSet<ParentEntity>();

	@OneToMany(mappedBy = "studentGrade", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private List<GradeRecordEntity> grades = new ArrayList<>();

	public StudentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SchoolClassEntity getSchoolClass() {
		return studentSchoolClass;
	}

	public void setSchoolClass(SchoolClassEntity schoolClass) {
		this.studentSchoolClass = schoolClass;
	}

	public SchoolYearEntity getSchoolYear() {
		return schoolYearStudent;
	}

	public void setSchoolYear(SchoolYearEntity schoolYear) {
		this.schoolYearStudent = schoolYear;
	}

	public Set<ParentEntity> getParent() {
		return parent;
	}

	public void setParent(Set<ParentEntity> parent) {
		this.parent = parent;
	}

}
