package com.iktpreobuka.schoolEDiary.services;

import java.util.Set;

import com.iktpreobuka.schoolEDiary.entities.GradeRecordEntity;
import com.iktpreobuka.schoolEDiary.entities.StudentEntity;


public interface ParentDAO {

	public Set<StudentEntity> findAllChildren(String parentUsername);	
	public Set<GradeRecordEntity> findChildsGrade(String parentUsername);
}
