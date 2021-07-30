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
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TeacherEntity extends UserEntity {

	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)// prepravljeno u all
	@JoinTable(name = "Teacher_Class", joinColumns = {
			@JoinColumn(name = "Teacher_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "Class_id", nullable = false, updatable = false) })
	protected Set<SchoolClassEntity> classes = new HashSet<SchoolClassEntity>();

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)// prepravljeno u all
	@JoinTable(name = "Teacher_Subject", joinColumns = {
			@JoinColumn(name = "Teacher_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "Subject_id", nullable = false, updatable = false) })
	protected Set<SubjectEntity> subjects = new HashSet<SubjectEntity>();
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "teacherGrade", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })// prepravljeno u all
	private List<GradeRecordEntity> grades = new ArrayList<>();

	public Set<SchoolClassEntity> getClasses() {
		return classes;
	}

	public void setClasses(Set<SchoolClassEntity> classes) {
		this.classes = classes;
	}

	public Set<SubjectEntity> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<SubjectEntity> subjects) {
		this.subjects = subjects;
	}

	public List<GradeRecordEntity> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeRecordEntity> grades) {
		this.grades = grades;
	}

	
}
