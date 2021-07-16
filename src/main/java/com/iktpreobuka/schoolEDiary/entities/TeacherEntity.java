package com.iktpreobuka.schoolEDiary.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("Teacher")
public class TeacherEntity extends UserEntity {

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "Teacher_Class", joinColumns = {
			@JoinColumn(name = "Class_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "Teacher_id", nullable = false, updatable = false) })
	protected Set<ClassRoomEntity> classes = new HashSet<ClassRoomEntity>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "Teacher_Subject", joinColumns = {
			@JoinColumn(name = "Subject_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "Teacher_id", nullable = false, updatable = false) })
	protected Set<SubjectEntity> subjects = new HashSet<SubjectEntity>();

}
