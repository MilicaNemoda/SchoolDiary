package com.iktpreobuka.schoolEDiary.services;

import java.util.Set;

import com.iktpreobuka.schoolEDiary.entities.StudentEntity;
import com.iktpreobuka.schoolEDiary.entities.SubjectEntity;

public interface TeacherDAO {
	public void removeTeacherEntity(String username);
	public Set<StudentEntity> findTeachersStudentsForTheSubject(String teacherUsername, String subjectName);
	public Set<StudentEntity> findAllTeachersStudents(String teacherUsername);
	public Set<SubjectEntity> findAllTeachersSubjects(String teacherUsername);
}
