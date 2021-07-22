package com.iktpreobuka.schoolEDiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.schoolEDiary.controllers.utils.RESTError;
import com.iktpreobuka.schoolEDiary.entities.GradeRecordEntity;
import com.iktpreobuka.schoolEDiary.entities.StudentEntity;
import com.iktpreobuka.schoolEDiary.entities.SubjectEntity;
import com.iktpreobuka.schoolEDiary.entities.TeacherEntity;
import com.iktpreobuka.schoolEDiary.entities.DTO.GradeDTO;
import com.iktpreobuka.schoolEDiary.repositories.GradeRepository;
import com.iktpreobuka.schoolEDiary.repositories.StudentRepository;
import com.iktpreobuka.schoolEDiary.repositories.SubjectRepository;
import com.iktpreobuka.schoolEDiary.repositories.TeacherRepository;
import com.iktpreobuka.schoolEDiary.services.GradeRecordDAOImpl;

@RestController
@RequestMapping(value = "/api/v1/schoolEDiary/teacher")
public class TeacherController {
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	TeacherRepository teacherRepository;
	
	@Autowired
	SubjectRepository subjectRepository;
	
	@Autowired
	GradeRepository gradeRepository;
	
	@Autowired
	GradeRecordDAOImpl gradeRecordDAOImpl;

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/grade")
	public ResponseEntity<?> addGradeToStudent(@PathVariable Integer id,  @RequestBody GradeDTO grade) {
//			if (GradeRecordDAOImpl) {
//			return new ResponseEntity<RESTError>(new RESTError(400, "Grade parameters are not complete."),
//					HttpStatus.BAD_REQUEST);
//		}
		TeacherEntity teacher = teacherRepository.findById(id).get();
		StudentEntity student = studentRepository.findByUsername(grade.getStudentUsername()).get();
		if (student == null) {
			return new ResponseEntity<RESTError>(new RESTError(400, "User not found"), HttpStatus.NOT_FOUND);
		}
		SubjectEntity subject = subjectRepository.findByName(grade.getSubjectName()).get();
		// TODO validacija
		GradeRecordEntity newGrade = new GradeRecordEntity();
		newGrade.setGradeType(grade.getGradeType());
		newGrade.setName(grade.getName());
		newGrade.setStudentGrade(student);
		newGrade.setSubjectGrade(subject);
		newGrade.setTeacherGrade(teacher);
		gradeRepository.save(newGrade);
		return new ResponseEntity<GradeRecordEntity>(newGrade, HttpStatus.OK);
		// automatski ce biti sacuvana i adresa/ return user;//prepravljeno sa slajda
		// 102
	}
}
