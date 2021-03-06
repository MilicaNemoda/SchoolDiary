package com.iktpreobuka.schoolEDiary.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.schoolEDiary.entities.StudentEntity;
import com.iktpreobuka.schoolEDiary.entities.TeacherEntity;
import com.iktpreobuka.schoolEDiary.repositories.TeacherRepository;

@Service
public class StudentDAOImpl implements StudentDAO {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	TeacherRepository teacherRepository;
	
	@Transactional
	public void removeStudentEntity(Integer id) {

		String sql1 = "delete from db_school_e_diary.parent_student where student_id = " + id;
		String sql2= "delete from db_school_e_diary.grade_record_entity where student_grade = " + id;
		String sql3 = "delete from db_school_e_diary.student_entity where id = " + id;
		String sql4 = "delete from db_school_e_diary.user_entity where id = " + id;

		em.createNativeQuery(sql1).executeUpdate();		
		em.createNativeQuery(sql2).executeUpdate();
		em.createNativeQuery(sql3).executeUpdate();
		em.createNativeQuery(sql4).executeUpdate();

	}
	
	
}
