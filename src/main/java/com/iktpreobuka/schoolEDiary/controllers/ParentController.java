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
import com.iktpreobuka.schoolEDiary.repositories.ParentRepository;
import com.iktpreobuka.schoolEDiary.services.ParentDAOImpl;


@RestController
@RequestMapping(value = "/api/v1/schoolEDiary/parent")
public class ParentController {
	
	@Autowired
	ParentRepository parentRepository;
	
	@Autowired
	ParentDAOImpl parentDAOImpl;
	
	@Secured("ROLE_PARENT")
	@RequestMapping(method = RequestMethod.GET, value = "/grades")
	public ResponseEntity<?> getChildGrades(@RequestParam String parentUsername) {
		if (parentRepository.findByUsername(parentUsername) == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Parent not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Set<GradeRecordEntity>>(parentDAOImpl.findChildsGrade(parentUsername),
				HttpStatus.OK);
	}//radi
	
	@Secured("ROLE_PARENT")
	@RequestMapping(method = RequestMethod.GET, value = "/children")
	public ResponseEntity<?> getAllChildren(@RequestParam String parentUsername) {
		if (parentRepository.findByUsername(parentUsername) == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Parent not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Set<StudentEntity>>(parentDAOImpl.findAllChildren(parentUsername),
				HttpStatus.OK);
	}//radi
}
