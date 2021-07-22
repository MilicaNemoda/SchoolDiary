package com.iktpreobuka.schoolEDiary.services;

import java.util.List;

import com.iktpreobuka.schoolEDiary.entities.StudentEntity;

public interface GradeRecordDAO {

	public List<StudentEntity> findStudentBySubjectAndTeacher(String subjectName, String teacherName);
	
}
