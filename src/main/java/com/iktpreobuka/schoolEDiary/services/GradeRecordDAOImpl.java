package com.iktpreobuka.schoolEDiary.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.schoolEDiary.entities.StudentEntity;
import com.iktpreobuka.schoolEDiary.repositories.StudentRepository;
import com.iktpreobuka.schoolEDiary.repositories.SubjectRepository;
import com.iktpreobuka.schoolEDiary.repositories.TeacherRepository;

@Service
public class GradeRecordDAOImpl implements GradeRecordDAO {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	SubjectRepository subjectRepository;
	
	@Autowired
	TeacherRepository teacherRepository;
	
	@Autowired
	StudentRepository studentRepository;

	@Override
	public Set<StudentEntity> findStudentBySubjectAndTeacher(String subjectName, String teacherUsername) {

		
		Integer subjectId = subjectRepository.findByName(subjectName).get().getId();
		Integer teacherId = teacherRepository.findByUsername(teacherUsername).get().getId();
		
		String sql =  "select s.id from student_entity s, subject_entity sb, teacher_subject t "
				+ "where s.school_year_student = sb.school_year_subject and sb.id = t.subject_id " 
				+"and teacher_id = " + teacherId + " and subject_id = " + subjectId;
									
				Query query = em.createNativeQuery(sql);
				List<Integer> result = query.getResultList();
				
				Set<StudentEntity> students = new HashSet<StudentEntity>();
				for (Integer studentId : result) {
					students.add(studentRepository.findById(studentId).get());
					}
									
				return students;
	}
	
	public Set<StudentEntity> checkIfStudentListensSubject(String subjectName){
		Integer subjectId = subjectRepository.findByName(subjectName).get().getId();
				
		String sql =  "select s.id from student_entity s, subject_entity sb "
				+ "where s.school_year_student = sb.school_year_subject and " 
				+ "subject_id = " + subjectId;
									
				Query query = em.createNativeQuery(sql);
				List<Integer> result = query.getResultList();
				
				Set<StudentEntity> students = new HashSet<StudentEntity>();
				for (Integer studentId : result) {
					students.add(studentRepository.findById(studentId).get());
					}
									
				return students;
	}
}
