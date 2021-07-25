package com.iktpreobuka.schoolEDiary.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.schoolEDiary.repositories.TeacherRepository;

@Service
public class StudentDAOImpl implements StudentDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	TeacherRepository teacherRepository;
	
	@Transactional
	public void removeStudentEntity(Integer id){

		String sql1="delete from db_school_e_diary.parent_student where student_id = " + id;
//		String sql3="delete from db_school_e_diary.teacher_entity where id = " + id;
		String sql4="delete from db_school_e_diary.student_entity where id = " + id;
		
		 em.createNativeQuery(sql1).executeUpdate();
//		 em.createNativeQuery(sql3).executeUpdate();
		 em.createNativeQuery(sql4).executeUpdate();
			
	}
}
