package com.iktpreobuka.schoolEDiary.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
//@DiscriminatorValue("Student")
public class StudentEntity extends UserEntity {
	@Column
//	@JsonView(Views.Admin.class)
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
//	@ColumnDefault(value = "0001-01-01T00:00:00")
	private Integer yearOfBirth;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "studentSchoolClass")
	private SchoolClassEntity studentSchoolClass;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "schoolYear")
	private SchoolYearEntity schoolYear;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "Parent_Student", joinColumns = {
			@JoinColumn(name = "Student_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "Parent_id", nullable = false, updatable = false) })
	protected Set<ParentEntity> parent = new HashSet<ParentEntity>();

	@OneToMany(mappedBy = "studentGrade", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	private List<GradeRecordEntity> grades = new ArrayList<>();

	public StudentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

//	public Date getDateOfBirth() {
//		return dateOfBirth;
//	}
//
//	public void setDateOfBirth(Date dateOfBirth) {
//		this.dateOfBirth = dateOfBirth;
//	}

	public SchoolClassEntity getSchoolClass() {
		return studentSchoolClass;
	}

	public void setSchoolClass(SchoolClassEntity schoolClass) {
		this.studentSchoolClass = schoolClass;
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
