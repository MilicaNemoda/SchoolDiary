package com.iktpreobuka.schoolEDiary.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

public class StudentEntity extends UserEntity {

//	@JsonView(Views.Admin.class)
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date dateOfBirth;
		
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "student_classroom")
	private ClassRoomEntity classRoom;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "student_school_year")
	private SchoolYearEntity schoolYear;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "Parent_Student", joinColumns = {
			@JoinColumn(name = "Student_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "Parent_id", nullable = false, updatable = false) })
	protected Set<ParentEntity> parent = new HashSet<ParentEntity>();
	
	public StudentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public ClassRoomEntity getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(ClassRoomEntity classRoom) {
		this.classRoom = classRoom;
	}

	public SchoolYearEntity getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(SchoolYearEntity schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Set<ParentEntity> getParent() {
		return parent;
	}

	public void setParent(Set<ParentEntity> parent) {
		this.parent = parent;
	}
	
}
