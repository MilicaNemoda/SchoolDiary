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
import javax.persistence.ManyToOne;

import helper.EGradeType;

/**
 * Ovaj entity predstavlja ocenu i kao takav sadrzi naziv ocene, studenta kome
 * je data, predmet i profesora koji dodeljuje ocenu.
 */
@Entity
public class GradeRecordEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@JsonView(Views.Private.class)
//	@JsonProperty("ID")
	private Integer id;
	@Column(nullable = false)
	private Integer grade;
	@Column(nullable = false)
	private EGradeType gradeType;
		
	@Override
	public String toString() {
		return "GradeRecordEntity [id=" + id + ", grade=" + grade + ", gradeType=" + gradeType + ", studentGrade="
				+ studentGrade + ", subjectGrade=" + subjectGrade + ", teacherGrade=" + teacherGrade + "]";
	}

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
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

}
