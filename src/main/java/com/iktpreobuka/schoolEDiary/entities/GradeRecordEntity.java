package com.iktpreobuka.schoolEDiary.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import helper.EGradeType;

@Entity
public class GradeRecordEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@JsonView(Views.Private.class)
//	@JsonProperty("ID")
	private Integer id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private EGradeType gradeType;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "studentGrade")
	private StudentEntity studentGrade;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "subjectGrade")
	private SubjectEntity subjectGrade;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherGrade")
	private TeacherEntity teacherGrade;
	
	public GradeRecordEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EGradeType getGradeType() {
		return gradeType;
	}

	public void setGradeType(EGradeType gradeType) {
		this.gradeType = gradeType;
	}

	public StudentEntity getStudentGrade() {
		return studentGrade;
	}

	public void setStudentGrade(StudentEntity studentGrade) {
		this.studentGrade = studentGrade;
	}

	public SubjectEntity getSubjectGrade() {
		return subjectGrade;
	}

	public void setSubjectGrade(SubjectEntity subjectGrade) {
		this.subjectGrade = subjectGrade;
	}

	public TeacherEntity getTeacherGrade() {
		return teacherGrade;
	}

	public void setTeacherGrade(TeacherEntity teacherGrade) {
		this.teacherGrade = teacherGrade;
	}
	
}
