package com.iktpreobuka.schoolEDiary.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.schoolEDiary.controllers.utils.RESTError;
import com.iktpreobuka.schoolEDiary.entities.GradeRecordEntity;
import com.iktpreobuka.schoolEDiary.entities.StudentEntity;
import com.iktpreobuka.schoolEDiary.repositories.GradeRepository;
import com.iktpreobuka.schoolEDiary.repositories.StudentRepository;
import com.iktpreobuka.schoolEDiary.services.GradeRecordDAOImpl;
import com.iktpreobuka.schoolEDiary.services.StudentDAOImpl;

@RestController
@RequestMapping(value = "/api/v1/schoolEDiary/student")
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	GradeRepository gradeRepository;
	
	@Autowired
	StudentDAOImpl studentDAOImpl;
	
	@Autowired
	GradeRecordDAOImpl gradeDAOImpl;
	
	@Secured("ROLE_STUDENT")
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ResponseEntity<?> getStudentGrades(@RequestParam String studentUsername) {
		StudentEntity student = studentRepository.findByUsername(studentUsername).get();
		if (student == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Student not found."), HttpStatus.NOT_FOUND);
		}
		Set<GradeRecordEntity> grades = gradeDAOImpl.findAllGradesByStudent(studentUsername);
		return new ResponseEntity<Set<GradeRecordEntity>>(grades, HttpStatus.OK);
	}//radi
}
