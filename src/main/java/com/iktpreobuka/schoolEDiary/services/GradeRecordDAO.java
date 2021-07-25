package com.iktpreobuka.schoolEDiary.services;

import java.util.List;
import java.util.Set;

import com.iktpreobuka.schoolEDiary.entities.StudentEntity;

public interface GradeRecordDAO {

	public Set<StudentEntity> findStudentBySubjectAndTeacher(String subjectName, String teacherName);
	public Set<StudentEntity> checkIfStudentListensSubject(String subjectName);
	
}
