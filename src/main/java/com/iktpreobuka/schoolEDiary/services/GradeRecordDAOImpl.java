package com.iktpreobuka.schoolEDiary.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.iktpreobuka.schoolEDiary.entities.StudentEntity;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

@Service
public class GradeRecordDAOImpl implements GradeRecordDAO {
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<StudentEntity> findStudentBySubjectAndTeacher(String subjectName, String teacherName) {
		String sql = "select student_grade from GradeRecordEntity" 
	+ " where subject_grade = :subjectName and teacher_grade = :teacherName";
		
				Query query = em.createQuery(sql);
				query.setParameter("subject_grade", subjectName);
				query.setParameter("teacher_grade", teacherName);

				List<StudentEntity> result = new ArrayList<>();
				result =  query.getResultList();
				return result;
	}
}
