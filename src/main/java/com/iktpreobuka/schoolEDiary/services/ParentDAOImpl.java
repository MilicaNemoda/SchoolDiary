package com.iktpreobuka.schoolEDiary.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.schoolEDiary.entities.GradeRecordEntity;
import com.iktpreobuka.schoolEDiary.entities.ParentEntity;
import com.iktpreobuka.schoolEDiary.entities.StudentEntity;
import com.iktpreobuka.schoolEDiary.repositories.GradeRepository;
import com.iktpreobuka.schoolEDiary.repositories.ParentRepository;
import com.iktpreobuka.schoolEDiary.repositories.StudentRepository;

@Service
public class ParentDAOImpl implements ParentDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Autowired
	ParentRepository parentRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	GradeRepository gradeRepository;
	
	@Override
	public Set<StudentEntity> findAllChildren(String parentUsername){
		ParentEntity parent = parentRepository.findByUsername(parentUsername).get();
		
		String sql = "select student_id from parent_student where parent_id = " + parent.getId();
									
				Query query = em.createNativeQuery(sql);
				List<Integer> result = query.getResultList();
				
				Set<StudentEntity> children = new HashSet<StudentEntity>();
				for (Integer childId : result) {
				children.add(studentRepository.findById(childId).get());
					}
									
				return children;
		
	}
	
	@Override
	public Set<GradeRecordEntity> findChildsGrade(String parentUsername){
		ParentEntity parent = parentRepository.findByUsername(parentUsername).get();
		
		String sql = "select g.id from grade_record_entity g, parent_student ps where "
				+ "g.student_grade = ps.student_id and ps.parent_id = " + parent.getId();
									
				Query query = em.createNativeQuery(sql);
				List<Integer> result = query.getResultList();
				
				Set<GradeRecordEntity> grades = new HashSet<GradeRecordEntity>();
				for (Integer childId : result) {
					grades.add(gradeRepository.findById(childId).get());
					}
									
				return grades;
		
	}
}
