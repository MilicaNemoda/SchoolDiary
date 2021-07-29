package com.iktpreobuka.schoolEDiary.controllers;

import java.util.Set;
import java.util.function.Consumer;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.schoolEDiary.controllers.utils.RESTError;
import com.iktpreobuka.schoolEDiary.entities.GradeRecordEntity;
import com.iktpreobuka.schoolEDiary.entities.ParentEntity;
import com.iktpreobuka.schoolEDiary.entities.StudentEntity;
import com.iktpreobuka.schoolEDiary.entities.SubjectEntity;
import com.iktpreobuka.schoolEDiary.entities.TeacherEntity;
import com.iktpreobuka.schoolEDiary.entities.DTO.GradeDTO;
import com.iktpreobuka.schoolEDiary.models.EmailObject;
import com.iktpreobuka.schoolEDiary.repositories.GradeRepository;
import com.iktpreobuka.schoolEDiary.repositories.StudentRepository;
import com.iktpreobuka.schoolEDiary.repositories.SubjectRepository;
import com.iktpreobuka.schoolEDiary.repositories.TeacherRepository;
import com.iktpreobuka.schoolEDiary.services.EmailService;
import com.iktpreobuka.schoolEDiary.services.GradeRecordDAOImpl;
import com.iktpreobuka.schoolEDiary.services.TeacherDAOImpl;

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

	@Autowired
	TeacherDAOImpl teacherDAOImpl;
	
	@Autowired
	EmailService emailService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.POST, value = "/{teacherId}/grade")
	public ResponseEntity<?> addGradeToStudent(@PathVariable Integer teacherId, @Valid @RequestBody GradeDTO grade) {

		TeacherEntity teacher = teacherRepository.findById(teacherId).get();
		if (teacher == null) {
			return new ResponseEntity<RESTError>(new RESTError(400, "Teacher not found"), HttpStatus.NOT_FOUND);
		}
		StudentEntity student = studentRepository.findByUsername(grade.getStudentUsername()).get();
		if (student == null) {
			return new ResponseEntity<RESTError>(new RESTError(400, "User not found"), HttpStatus.NOT_FOUND);
		}
		SubjectEntity subject = subjectRepository.findByName(grade.getSubjectName()).get();
		if (subject == null) {
			return new ResponseEntity<RESTError>(new RESTError(400, "Subject not found"), HttpStatus.NOT_FOUND);
		}
		if (!gradeRecordDAOImpl.findStudentBySubjectAndTeacher(grade.getSubjectName(), teacher.getUsername())
				.contains(student)) {
			return new ResponseEntity<RESTError>(new RESTError(400, "Grade parameters are not complete."),
					HttpStatus.BAD_REQUEST);// stavi bolju poruku
		}
		GradeRecordEntity newGrade = new GradeRecordEntity();
		newGrade.setGradeType(grade.getGradeType());
		newGrade.setGrade(grade.getGrade());
		newGrade.setStudentGrade(student);
		newGrade.setSubjectGrade(subject);
		newGrade.setTeacherGrade(teacher);
		gradeRepository.save(newGrade);

		Set<ParentEntity> parents = gradeRecordDAOImpl.findParentOfStudentGotGrade(newGrade.getId());
		// sending e-mails to all child parents
		for (ParentEntity parentEntity : parents) {
			EmailObject emailObject = new EmailObject();
			emailObject.setMailReceiver(parentEntity.getEmail());
			emailObject.setSubject("Your child received a new school grade!");
			emailObject.setText("Your child received " + grade.getGrade() + " from " + grade.getSubjectName()
					+ " teeched by " + teacher.getFirstName() + " " + teacher.getLastName());

			emailService.sendSimpleMessage(emailObject);
		}

		return new ResponseEntity<GradeRecordEntity>(newGrade, HttpStatus.OK);
	}
	// Put grade
	// Delete grade

	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.GET, value = "/studentsBySubject")
	public ResponseEntity<?> getTeachersStudentsBySubject(@RequestParam String teacherUsername,
			@RequestParam String subjectName) {
		if (teacherRepository.findByUsername(teacherUsername) == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Teacher not found"), HttpStatus.NOT_FOUND);
		}
		if (subjectRepository.findByName(subjectName) == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Subject not found"), HttpStatus.NOT_FOUND);
		}
		if (teacherDAOImpl.findTeachersStudentsForTheSubject(teacherUsername, subjectName) == null) {
			return new ResponseEntity<RESTError>(
					new RESTError(404, "There arn't students for that teacher and subject."), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Set<StudentEntity>>(
				teacherDAOImpl.findTeachersStudentsForTheSubject(teacherUsername, subjectName), HttpStatus.OK);
	}

	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.GET, value = "/allStudents")
	public ResponseEntity<?> getTeachersStudents(@RequestParam String teacherUsername) {
		if (teacherRepository.findByUsername(teacherUsername) == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Teacher not found"), HttpStatus.NOT_FOUND);
		}
		if (teacherDAOImpl.findAllTeachersStudents(teacherUsername) == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "There arn't students for that teacher."),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Set<StudentEntity>>(teacherDAOImpl.findAllTeachersStudents(teacherUsername),
				HttpStatus.OK);
	}

	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.GET, value = "/subject")
	public ResponseEntity<?> getTeachersSubjects(@RequestParam String teacherUsername) {
		if (teacherRepository.findByUsername(teacherUsername) == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Teacher not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Set<SubjectEntity>>(teacherDAOImpl.findAllTeachersSubjects(teacherUsername),
				HttpStatus.OK);
	}
}
