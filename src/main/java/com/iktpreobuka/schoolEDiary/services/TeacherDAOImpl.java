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
import com.iktpreobuka.schoolEDiary.entities.SubjectEntity;
import com.iktpreobuka.schoolEDiary.entities.TeacherEntity;
import com.iktpreobuka.schoolEDiary.repositories.StudentRepository;
import com.iktpreobuka.schoolEDiary.repositories.SubjectRepository;
import com.iktpreobuka.schoolEDiary.repositories.TeacherRepository;

@Service
public class TeacherDAOImpl implements TeacherDAO  {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	TeacherRepository teacherRepository;
	
	@Autowired
	SubjectRepository subjectRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Transactional
	public void removeTeacherEntity(String username){
		TeacherEntity deletedTeacher = teacherRepository.findByUsername(username).get();
		
		String sql1 = "delete from db_school_e_diary.teacher_subject where teacher_id = " + deletedTeacher.getId();
		String sql2= "delete from db_school_e_diary.grade_record_entity where teacher_grade = " + deletedTeacher.getId();
		String sql3 = "delete from db_school_e_diary.teacher_class where teacher_id = " + deletedTeacher.getId();
		String sql4="delete from db_school_e_diary.teacher_entity where id = " + deletedTeacher.getId();
		
		 em.createNativeQuery(sql1).executeUpdate();
		 em.createNativeQuery(sql2).executeUpdate();
		 em.createNativeQuery(sql3).executeUpdate();
		 em.createNativeQuery(sql4).executeUpdate();
			
	}
	
	//Nadji studente kojima profesor predaje odredjen predmet
	public Set<StudentEntity> findStudentsByTeacherandSubject(String teacherUsername, String subjectName) {
		TeacherEntity teacher = teacherRepository.findByUsername(teacherUsername).get();
		SubjectEntity subject = subjectRepository.findByName(subjectName).get();
				
		String sql =  "select s.id from student_entity s, teacher_Class tc, teacher_subject ts "
				+ "where s.student_school_class = tc.class_id and tc.teacher_id = ts.teacher_id " 
				+ "and tc.teacher_id = " + teacher.getId() + " and ts.subject_id = " + subject.getId();
									
				Query query = em.createNativeQuery(sql);
				List<Integer> result = query.getResultList();
				
				Set<StudentEntity> students = new HashSet<StudentEntity>();
				for (Integer studentId : result) {
					students.add(studentRepository.findById(studentId).get());
					}
									
				return students;
		}
	
	//Nadji sve studente nekog profesora
	@Override
	public Set<StudentEntity> findAllStudentsByTeacher(String teacherUsername) {
		TeacherEntity teacher = teacherRepository.findByUsername(teacherUsername).get();
						
		String sql =  "select s.id from student_entity s, teacher_Class tc, teacher_subject ts "
				+ "where s.student_school_class = tc.class_id and tc.teacher_id = ts.teacher_id " 
				+ "and tc.teacher_id = " + teacher.getId();
									
				Query query = em.createNativeQuery(sql);
				List<Integer> result = query.getResultList();
				
				Set<StudentEntity> students = new HashSet<StudentEntity>();
				for (Integer studentId : result) {
					students.add(studentRepository.findById(studentId).get());
					}
									
				return students;
		}
	
	@Override
	public Set<SubjectEntity> findAllSubjectsByTeacher(String teacherUsername){
		TeacherEntity teacher = teacherRepository.findByUsername(teacherUsername).get();
		
		String sql = "select subject_id from teacher_subject where teacher_id = " + teacher.getId();
									
				Query query = em.createNativeQuery(sql);
				List<Integer> result = query.getResultList();
				
				Set<SubjectEntity> subjects = new HashSet<SubjectEntity>();
				for (Integer subjectId : result) {
					subjects.add(subjectRepository.findById(subjectId).get());
					}
									
				return subjects;
		
	}
	
}
