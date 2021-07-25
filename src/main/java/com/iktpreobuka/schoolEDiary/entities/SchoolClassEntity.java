package com.iktpreobuka.schoolEDiary.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.schoolEDiary.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SchoolClassEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(nullable = false)
	private String name;

	@JsonView(Views.Private.class)
	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)// prepravljeno u all
	@JoinTable(name = "Teacher_Class", joinColumns = {
			@JoinColumn(name = "Class_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "Teacher_id", nullable = false, updatable = false) })
	private Set<TeacherEntity> teachers = new HashSet<TeacherEntity>();

	@JsonIgnore
	@OneToMany(mappedBy = "studentSchoolClass", fetch = FetchType.LAZY, cascade =  CascadeType.REFRESH)// prepravljeno u all
	private List<StudentEntity> students = new ArrayList<>();

	public SchoolClassEntity() {
		super();
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

//	public ClassHeadTeacherEntity getClassElder() {
//		return classElder;
//	}
//
//	public void setClassElder(ClassHeadTeacherEntity classElder) {
//		this.classElder = classElder;
//	}

	public List<StudentEntity> getStudents() {
		return students;
	}

	public Set<TeacherEntity> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<TeacherEntity> teachers) {
		this.teachers = teachers;
	}

	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}

}
