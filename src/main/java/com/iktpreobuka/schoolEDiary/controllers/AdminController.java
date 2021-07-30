package com.iktpreobuka.schoolEDiary.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.schoolEDiary.controllers.utils.Encryption;
import com.iktpreobuka.schoolEDiary.controllers.utils.RESTError;
import com.iktpreobuka.schoolEDiary.controllers.utils.UserCustomValidator;
import com.iktpreobuka.schoolEDiary.entities.AddressEntity;
import com.iktpreobuka.schoolEDiary.entities.GradeRecordEntity;
import com.iktpreobuka.schoolEDiary.entities.ParentEntity;
import com.iktpreobuka.schoolEDiary.entities.SchoolClassEntity;
import com.iktpreobuka.schoolEDiary.entities.SchoolYearEntity;
import com.iktpreobuka.schoolEDiary.entities.StudentEntity;
import com.iktpreobuka.schoolEDiary.entities.SubjectEntity;
import com.iktpreobuka.schoolEDiary.entities.TeacherEntity;
import com.iktpreobuka.schoolEDiary.entities.UserEntity;
import com.iktpreobuka.schoolEDiary.entities.DTO.GradeDTO;
import com.iktpreobuka.schoolEDiary.entities.DTO.ParentDTO;
import com.iktpreobuka.schoolEDiary.entities.DTO.SchoolClassDTO;
import com.iktpreobuka.schoolEDiary.entities.DTO.StudentDTO;
import com.iktpreobuka.schoolEDiary.entities.DTO.SubjectDTO;
import com.iktpreobuka.schoolEDiary.entities.DTO.TeacherDTO;
import com.iktpreobuka.schoolEDiary.repositories.AddressRepository;
import com.iktpreobuka.schoolEDiary.repositories.GradeRepository;
import com.iktpreobuka.schoolEDiary.repositories.ParentRepository;
import com.iktpreobuka.schoolEDiary.repositories.SchoolClassRepository;
import com.iktpreobuka.schoolEDiary.repositories.SchoolYearRepository;
import com.iktpreobuka.schoolEDiary.repositories.StudentRepository;
import com.iktpreobuka.schoolEDiary.repositories.SubjectRepository;
import com.iktpreobuka.schoolEDiary.repositories.TeacherRepository;
import com.iktpreobuka.schoolEDiary.repositories.UserRepository;
import com.iktpreobuka.schoolEDiary.services.GradeRecordDAOImpl;
import com.iktpreobuka.schoolEDiary.services.StudentDAOImpl;
import com.iktpreobuka.schoolEDiary.services.TeacherDAOImpl;

@RestController
@RequestMapping(value = "/api/v1/schoolEDiary/admin") // api application program interface
public class AdminController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ParentRepository parentRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private SchoolYearRepository schoolYearRepository;

	@Autowired
	private SchoolClassRepository schoolClassRepository;

	@Autowired
	private TeacherDAOImpl teacherDAOImpl;

	@Autowired
	private StudentDAOImpl studentDAOImpl;

	@Autowired
	private GradeRecordDAOImpl gadeRecordDAOImpl;

	@Autowired
	UserCustomValidator UserCustomValidator;

	@InitBinder
	protected void initUserCustomValidatorBinder(final WebDataBinder binder) {
		binder.setValidator(UserCustomValidator);
	}

// -- POST teacher
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/teacher")
	public ResponseEntity<?> addTeacher(@Valid @RequestBody TeacherDTO teacher, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			UserCustomValidator.validate(teacher, result);
		}

		if (teacherRepository.findByUsername(teacher.getUsername()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(440, "Username already used, choose another."),
					HttpStatus.FORBIDDEN);
		}

		Set<SubjectEntity> subjects = new HashSet<SubjectEntity>();
		for (String subject : teacher.getSubjects()) {
			if (subjectRepository.findByName(subject).get() == null) {
				return new ResponseEntity<RESTError>(new RESTError(400, "Subject not found."), HttpStatus.NOT_FOUND);
			}
			subjects.add(subjectRepository.findByName(subject).get());
		}

		Set<SchoolClassEntity> schoolClasses = new HashSet<SchoolClassEntity>();
		for (String schoolClass : teacher.getClasses()) {
			if (schoolClassRepository.findByName(schoolClass).get() == null) {
				return new ResponseEntity<RESTError>(new RESTError(400, "Student not found."), HttpStatus.NOT_FOUND);
			}
			schoolClasses.add(schoolClassRepository.findByName(schoolClass).get());
		}

		TeacherEntity newTeacher = new TeacherEntity();

		newTeacher.setUsername(teacher.getUsername());
		newTeacher.setEmail(teacher.getEmail());
		newTeacher.setPassword(Encryption.getPassEncoded(teacher.getPassword()));
		newTeacher.setFirstName(teacher.getFirstName());
		newTeacher.setLastName(teacher.getLastName());
		newTeacher.setRole(teacher.getRole());
		newTeacher.setSubjects(subjects);
		newTeacher.setClasses(schoolClasses);

		return new ResponseEntity<TeacherEntity>(teacherRepository.save(newTeacher), HttpStatus.OK);
	}

	// --POST Student
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/student")
	public ResponseEntity<?> addStudent(@Valid @RequestBody StudentDTO student, BindingResult result) {
		
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			UserCustomValidator.validate(student, result);
		}

		if (studentRepository.findByUsername(student.getUsername()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(444, "Username already used, choose another."),
					HttpStatus.FORBIDDEN);
		}

		SchoolYearEntity schoolYear = schoolYearRepository.findByYear(student.getYear()).get();
		if (schoolYear == null) {
			return new ResponseEntity<RESTError>(new RESTError(400, "Wrong school year."), HttpStatus.BAD_REQUEST);
		}

		SchoolClassEntity schoolClass = schoolClassRepository.findByName(student.getSchoolClass()).get();
		if (schoolClass == null) {
			return new ResponseEntity<RESTError>(new RESTError(400, "Wrong school class."), HttpStatus.BAD_REQUEST);
		}

		if (schoolYear.getYear().toString().charAt(0) != (schoolClass.getName()).charAt(0)) {
			return new ResponseEntity<RESTError>(new RESTError(400, "School year and school class do not match."),
					HttpStatus.BAD_REQUEST);
		}

		StudentEntity newStudent = new StudentEntity();

		newStudent.setUsername(student.getUsername());
		newStudent.setEmail(student.getEmail());
		newStudent.setPassword(Encryption.getPassEncoded(student.getPassword()));
		newStudent.setFirstName(student.getFirstName());
		newStudent.setLastName(student.getLastName());
		newStudent.setRole(student.getRole());
		newStudent.setSchoolYear(schoolYear);
		newStudent.setSchoolClass(schoolClass);
		return new ResponseEntity<StudentEntity>(studentRepository.save(newStudent), HttpStatus.OK);
	}

	// --POST parent
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/parent")
	public ResponseEntity<?> addParent(@Valid @RequestBody ParentDTO parent, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			UserCustomValidator.validate(parent, result);
		}
		Set<StudentEntity> children = new HashSet<StudentEntity>();
		for (String childUsername : parent.getChildUsernames()) {
			if (studentRepository.findByUsername(childUsername).get() == null) {
				return new ResponseEntity<RESTError>(new RESTError(400, "Student not found."), HttpStatus.NOT_FOUND);
			}
			children.add(studentRepository.findByUsername(childUsername).get());
		}

		ParentEntity newParent = new ParentEntity();
		newParent.setUsername(parent.getUsername());
		newParent.setEmail(parent.getEmail());
		newParent.setPassword(Encryption.getPassEncoded(parent.getPassword()));
		newParent.setFirstName(parent.getFirstName());
		newParent.setLastName(parent.getLastName());
		newParent.setRole(parent.getRole());
		newParent.setStudent(children);
		parentRepository.save(newParent);
		return new ResponseEntity<ParentEntity>(newParent, HttpStatus.OK);
	}// radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/grade")
	public ResponseEntity<?> addGrade(@RequestBody GradeDTO grade) {

		StudentEntity student = studentRepository.findByUsername(grade.getStudentUsername()).get();
		if (student == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "User not found"), HttpStatus.NOT_FOUND);
		}
		SubjectEntity subject = subjectRepository.findByName(grade.getSubjectName()).get();
		if (subject == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Subject not found"), HttpStatus.NOT_FOUND);
		}
		if (!gadeRecordDAOImpl.checkIfStudentListensSubject(subject.getName()).contains(student)) {
			return new ResponseEntity<RESTError>(new RESTError(400, "Student does not listen the subject."),
					HttpStatus.BAD_REQUEST);
		}

		GradeRecordEntity newGrade = new GradeRecordEntity();
		newGrade.setGradeType(grade.getGradeType());
		newGrade.setGrade(grade.getGrade());
		newGrade.setStudentGrade(student);
		newGrade.setSubjectGrade(subject);
		gradeRepository.save(newGrade);
		return new ResponseEntity<GradeRecordEntity>(newGrade, HttpStatus.OK);

	}// radi

	// --POST address
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/address")
	public ResponseEntity<?> addAddress(@Valid @RequestBody AddressEntity newAddress, BindingResult result) {
		if (addressRepository.findByStreet(newAddress.getStreet()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(440, "Address already created."), HttpStatus.FORBIDDEN);
		}
		// promeni ovo 444, i sta bi jos moglo da se doda od validacije?
		// proveri sta sve treba od validacije???!!!

		return new ResponseEntity<AddressEntity>(addressRepository.save(newAddress), HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/schoolYear")
	public ResponseEntity<?> addSchoolYear(@Valid @RequestBody SchoolYearEntity newSchoolYear, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		if (schoolYearRepository.findByYear(newSchoolYear.getYear()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(403, "Year already created."), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<SchoolYearEntity>(schoolYearRepository.save(newSchoolYear), HttpStatus.OK);
	}// radi

	// --POST subject
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/subject")
	public ResponseEntity<?> addSubject(@Valid @RequestBody SubjectDTO subject, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		if (subjectRepository.findByName(subject.getName()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(440, "Subject already created."), HttpStatus.FORBIDDEN);
		}
		SchoolYearEntity schoolYear = schoolYearRepository.findByYear(subject.getYear()).get();
		if (schoolYear == null) {
			return new ResponseEntity<RESTError>(new RESTError(400, "Wrong school year."), HttpStatus.BAD_REQUEST);
		}

		SubjectEntity newSubject = new SubjectEntity();
		newSubject.setName(subject.getName());
		newSubject.setWeeklyNumberOfLectures(subject.getWeeklyNumberOfLectures());
		newSubject.setSchoolYear(schoolYear);

		return new ResponseEntity<SubjectEntity>(subjectRepository.save(newSubject), HttpStatus.OK);
	}// radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/schoolClass")
	public ResponseEntity<?> addClassRoom(@Valid @RequestBody SchoolClassDTO newSchoolClass, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		if (schoolClassRepository.findByName(newSchoolClass.getName()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(404, "School class already created."),
					HttpStatus.FORBIDDEN);
		}
		SchoolClassEntity schoolClass = new SchoolClassEntity();
		schoolClass.setName(newSchoolClass.getName());
		return new ResponseEntity<SchoolClassEntity>(schoolClassRepository.save(schoolClass), HttpStatus.OK);
	}// radi

//	@Secured("ROLE_ADMIN")
//	@RequestMapping(method = RequestMethod.DELETE, value = "/schoolYear/{id}")
//	public ResponseEntity<?> removeSchoolClass(@PathVariable Integer id) {
//		SchoolYearEntity schoolYear = schoolYearRepository.findById(id).get();
//		schoolYearRepository.delete(schoolYear);
//		return new ResponseEntity<SchoolYearEntity>(schoolYear, HttpStatus.OK);
//	}
//
//	@Secured("ROLE_ADMIN")
//	@RequestMapping(method = RequestMethod.DELETE, value = "/schoolYear/{id}")
//	public ResponseEntity<?> removeSchoolYear(@PathVariable Integer id) {
//		SchoolYearEntity schoolYear = schoolYearRepository.findById(id).get();
//		schoolYearRepository.delete(schoolYear);
//		return new ResponseEntity<SchoolYearEntity>(schoolYear, HttpStatus.OK);
//	} valjda ne treba

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/teacher/{username}")
	public ResponseEntity<?> removeTeracher(@PathVariable String username) {
		TeacherEntity deletedTeacher = teacherRepository.findByUsername(username).get();
		if (deletedTeacher == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Student not found."), HttpStatus.NOT_FOUND);
		}
		teacherDAOImpl.removeTeacherEntity(username);
		return new ResponseEntity<TeacherEntity>(deletedTeacher, HttpStatus.OK);
	}// radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/parent/{username}")
	public ResponseEntity<?> removeParent(@PathVariable String username) {
		ParentEntity parent = parentRepository.findByUsername(username).get();
		if (parent == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Parent not found."), HttpStatus.NOT_FOUND);
		}
		parentRepository.delete(parent);// Radi ali ne ispisuje sta je obriso i teba provera da li taj roditelj
										// postoji!!
		return new ResponseEntity<ParentEntity>(parent, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/student/{username}")
	public ResponseEntity<?> removeStudent(@PathVariable String username) {
		StudentEntity student = studentRepository.findByUsername(username).get();
		if (student == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Student not found."), HttpStatus.NOT_FOUND);
		}
		studentDAOImpl.removeStudentEntity(student.getId());
		return new ResponseEntity<StudentEntity>(student, HttpStatus.OK);
	}// radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/subject/{id}")
	public ResponseEntity<?> removeSubject(@PathVariable String subjectName) {
		SubjectEntity subject = subjectRepository.findByName(subjectName).get();
		if (subject == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Subject not found."), HttpStatus.NOT_FOUND);
		}
		subjectRepository.delete(subject);
		return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/grade/{id}")
	public ResponseEntity<?> removeGrade(@PathVariable Integer id) {
		GradeRecordEntity grade = gradeRepository.findById(id).get();
		if (grade == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Grade not found."), HttpStatus.NOT_FOUND);
		}
		gradeRepository.delete(grade);
		return new ResponseEntity<GradeRecordEntity>(grade, HttpStatus.OK);
	}

//TODO PUT

	// Ne radi!!
//	@RequestMapping(method = RequestMethod.PUT, value = "/teachersClasses/{teacherId}")
//	public ResponseEntity<?> changeClassesToTeacher(@RequestBody TeacherDTO teacher, @PathVariable Integer teacherId) {
//
//		Set<SchoolClassEntity> schoolClasses = new HashSet<SchoolClassEntity>();
//		for (String schoolClass : teacher.getClasses()) {
//			if (schoolClassRepository.findByName(schoolClass).get() == null) {
//				return new ResponseEntity<RESTError>(new RESTError(404, "Classes not found."), HttpStatus.NOT_FOUND);
//			}
//			schoolClasses.add(schoolClassRepository.findByName(schoolClass).get());
//		}
//
//		TeacherEntity updatedTeacher = teacherRepository.findById(teacherId).get();
//		updatedTeacher.setClasses(schoolClasses);
//		return new ResponseEntity<TeacherEntity>(updatedTeacher, HttpStatus.OK);
//	}

//	@RequestMapping(method = RequestMethod.PUT, value = "/teachersClasses/{teacherId}")
//	public ResponseEntity<?> changeSubjectsToTeacher(@RequestBody TeacherDTO teacher, @PathVariable Integer teacherId) {
//
//		Set<SchoolClassEntity> schoolClasses = new HashSet<SchoolClassEntity>();
//		for (String schoolClass : teacher.getClasses()) {
//			if (schoolClassRepository.findByName(schoolClass).get() == null) {
//				return new ResponseEntity<RESTError>(new RESTError(404, "Classes not found."), HttpStatus.NOT_FOUND);
//			}
//			schoolClasses.add(schoolClassRepository.findByName(schoolClass).get());
//		}
//
//		TeacherEntity updatedTeacher = teacherRepository.findById(teacherId).get();
//		updatedTeacher.setClasses(schoolClasses);
//		return new ResponseEntity<TeacherEntity>(updatedTeacher, HttpStatus.OK);
//	}

	// TODO: match schoolClass - schoolYear, schoolClassStudent

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/teacher/{username}")
	public ResponseEntity<?> teacherUpdate(@RequestBody TeacherDTO updatedTeacher, @PathVariable String username) {

		if (teacherRepository.findByUsername(username).isPresent()) {
			TeacherEntity teacherEntity = teacherRepository.findByUsername(username).get();

			if (updatedTeacher.getFirstName() != null) {
				teacherEntity.setFirstName(updatedTeacher.getFirstName());
			}
			if (updatedTeacher.getLastName() != null) {
				teacherEntity.setLastName(updatedTeacher.getLastName());
			}
			if (updatedTeacher.getEmail() != null) {
				teacherEntity.setEmail(updatedTeacher.getEmail());
			}
			if (updatedTeacher.getUsername() != null) {
				teacherEntity.setUsername(updatedTeacher.getUsername());
			}
			if (updatedTeacher.getClasses() != null) {
				Set<SchoolClassEntity> schoolClasses = new HashSet<SchoolClassEntity>();
				for (String schoolClass : updatedTeacher.getClasses()) {
					schoolClasses.add(schoolClassRepository.findByName(schoolClass).get());
				}
				teacherEntity.setClasses(schoolClasses);
			}
			if (updatedTeacher.getSubjects() != null) {
				Set<SubjectEntity> subjects = new HashSet<SubjectEntity>();
				for (String subject : updatedTeacher.getSubjects()) {
					subjects.add(subjectRepository.findByName(subject).get());
				}
				teacherEntity.setSubjects(subjects);
			}

			if (updatedTeacher.getPassword() != null) {
				if (!updatedTeacher.getPassword().equals(updatedTeacher.getRepeatedPassword())) {
					return new ResponseEntity<RESTError>(new RESTError(403, "Passwords not matching"),
							HttpStatus.FORBIDDEN);
				} else {
					teacherEntity.setPassword(Encryption.getPassEncoded(updatedTeacher.getPassword()));
				}
			}

			teacherRepository.save(teacherEntity);
			return new ResponseEntity<TeacherEntity>(teacherEntity, HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(404, "Teacher not found"), HttpStatus.NOT_FOUND);
	}// radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/student/{username}")
	public ResponseEntity<?> studentUpdate(@RequestBody StudentDTO updatedStudent, @PathVariable String username) {

		if (studentRepository.findByUsername(username).isPresent()) {
			StudentEntity studentEntity = studentRepository.findByUsername(username).get();

			if (updatedStudent.getFirstName() != null) {
				studentEntity.setFirstName(updatedStudent.getFirstName());
			}
			if (updatedStudent.getLastName() != null) {
				studentEntity.setLastName(updatedStudent.getLastName());
			}
			if (updatedStudent.getEmail() != null) {
				studentEntity.setEmail(updatedStudent.getEmail());
			}
			if (updatedStudent.getUsername() != null) {
				studentEntity.setUsername(updatedStudent.getUsername());
			}
			if (updatedStudent.getYear() != null) {
				studentEntity.setSchoolYear(schoolYearRepository.findByYear(updatedStudent.getYear()).get());
			}
			if (updatedStudent.getSchoolClass() != null) {
				studentEntity.setSchoolClass(schoolClassRepository.findByName(updatedStudent.getSchoolClass()).get());
			}
			if (updatedStudent.getYear().toString().charAt(0) != (updatedStudent.getSchoolClass()).charAt(0)) {
				return new ResponseEntity<RESTError>(new RESTError(400, "School year and school class do not match."),
						HttpStatus.BAD_REQUEST);
			}

			if (updatedStudent.getPassword() != null) {
				if (!updatedStudent.getPassword().equals(updatedStudent.getRepeatedPassword())) {
					return new ResponseEntity<RESTError>(new RESTError(403, "Passwords not matching"),
							HttpStatus.FORBIDDEN);
				} else {
					studentEntity.setPassword(Encryption.getPassEncoded(updatedStudent.getPassword()));
				}
			}

			studentRepository.save(studentEntity);
			return new ResponseEntity<StudentEntity>(studentEntity, HttpStatus.OK);

		}
		return new ResponseEntity<RESTError>(new RESTError(404, "Student not found"), HttpStatus.NOT_FOUND);
	}// radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/parent/{username}")
	public ResponseEntity<?> parentUpdate(@RequestBody ParentDTO updatedParent, @PathVariable String username) {

		if (parentRepository.findByUsername(username).isPresent()) {
			ParentEntity parentEntity = parentRepository.findByUsername(username).get();
			if (updatedParent.getFirstName() != null) {
				parentEntity.setFirstName(updatedParent.getFirstName());
			}
			if (updatedParent.getLastName() != null) {
				parentEntity.setLastName(updatedParent.getLastName());
			}
			if (updatedParent.getEmail() != null) {
				parentEntity.setEmail(updatedParent.getEmail());
			}
			if (updatedParent.getUsername() != null) {
				parentEntity.setUsername(updatedParent.getUsername());
			}

			if (updatedParent.getChildUsernames() != null) {
				Set<StudentEntity> children = new HashSet<StudentEntity>();
				for (String child : updatedParent.getChildUsernames()) {
					children.add(studentRepository.findByUsername(child).get());
				}
				parentEntity.setStudent(children);
			}

			if (updatedParent.getPassword() != null) {
				if (!updatedParent.getPassword().equals(updatedParent.getRepeatedPassword())) {
					return new ResponseEntity<RESTError>(new RESTError(403, "Passwords not matching"),
							HttpStatus.FORBIDDEN);
				} else {
					parentEntity.setPassword(Encryption.getPassEncoded(updatedParent.getPassword()));
				}
			}

			parentRepository.save(parentEntity);
			return new ResponseEntity<ParentEntity>(parentEntity, HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(404, "User not found"), HttpStatus.NOT_FOUND);

	}// radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/subject/{name}")
	public ResponseEntity<?> subjectUpdate(@RequestBody SubjectDTO updatedSubject, @PathVariable String name) {

		if (subjectRepository.findByName(name).isPresent()) {
			SubjectEntity subjectEntity = subjectRepository.findByName(name).get();

			if (updatedSubject.getWeeklyNumberOfLectures() != null) {
				subjectEntity.setWeeklyNumberOfLectures(updatedSubject.getWeeklyNumberOfLectures());
			}
			if (updatedSubject.getYear() != null) {
				subjectEntity.setSchoolYear(schoolYearRepository.findByYear(updatedSubject.getYear()).get());
			}

			subjectRepository.save(subjectEntity);
			return new ResponseEntity<SubjectEntity>(subjectEntity, HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(404, "Subject not found"), HttpStatus.NOT_FOUND);
	}// TODO Nadji zasto ne radi

//	@Secured("ROLE_ADMIN")
//	@RequestMapping(method = RequestMethod.PUT, value = "/changePassword/{id}")
//	public ResponseEntity<?> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword,
//			@PathVariable Integer id) {
//
//		UserEntity user = userRepository.findById(id).get();
//
//		if (user == null)
//			return new ResponseEntity<RESTError>(new RESTError(400, "User not found"), HttpStatus.NOT_FOUND);
//
//		if (user.getPassword().equals(oldPassword))
//			user.setPassword(newPassword);
//
//		userRepository.save(user);
//		return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
//	}

	// TODO: PUT Povezivanje studenta sa roditeljima,
	// profesor i predmet, ucenik i odeljenje, ucenik i razred

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{userId}/addressId")
	public ResponseEntity<?> addAddressToUser(@PathVariable Integer id, @RequestBody AddressEntity adr) {
		if (adr.getStreet().equals(null) || adr.getCountry().equals(null) || adr.getCity().equals(null)) {
			return new ResponseEntity<RESTError>(new RESTError(400, "Grade parameters are not complete."),
					HttpStatus.BAD_REQUEST);
		}
		UserEntity user = userRepository.findById(id).get();
		if (user == null) {
			return new ResponseEntity<RESTError>(new RESTError(400, "User not found"), HttpStatus.NOT_FOUND);
		}
		addressRepository.save(adr);
		user.setAddress(adr);
		userRepository.save(user);
		return new ResponseEntity<UserEntity>(user, HttpStatus.OK);

	}

//	@RequestMapping(method = RequestMethod.PUT, value = "/grade")
//	public ResponseEntity<?> changeGrade(@RequestBody GradeDTO grade) {
////			if (newGrade.getName().equals(null) || newGrade.getId().equals(null) || newGrade.getGradeType().equals(null)) {
////			return new ResponseEntity<RESTError>(new RESTError(400, "Grade parameters are not complete."),
////					HttpStatus.BAD_REQUEST);
//////		}
////			StudentEntity student = studentRepository.findByUsername(grade.getStudentUsername()).get();
////			if (student == null) {
////				return new ResponseEntity<RESTError>(new RESTError(400, "User not found"), HttpStatus.NOT_FOUND);}
////			SubjectEntity subject = subjectRepository.findByName(grade.getSubjectName()).get();
////			GradeRecordEntity newGrade = new GradeRecordEntity();
////			newGrade.setStudentGrade(student);
////			newGrade.setSubjectGrade(subject);
////			gradeRepository.save(newGrade);
////			return new ResponseEntity<GradeRecordEntity>(newGrade, HttpStatus.OK);
////			// automatski ce biti sacuvana i adresa/ return user;//prepravljeno sa slajda 102
//		return null;
//	}

	// GET Predmeta Nastavnika Učenika Roditelja Ocena

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/teacher/{username}")
	public ResponseEntity<?> getTeracher(@PathVariable String username) {
		TeacherEntity teacher = teacherRepository.findByUsername(username).get();
		return new ResponseEntity<TeacherEntity>(teacher, HttpStatus.OK);
	}// Radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/teacher")
	public ResponseEntity<?> getAllTerachers() {
		List<TeacherEntity> teacher = (List<TeacherEntity>) teacherRepository.findAll();
		return new ResponseEntity<List<TeacherEntity>>(teacher, HttpStatus.OK);
	}// Radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/parent/{username}")
	public ResponseEntity<?> getParentByUsername(@PathVariable String username) {
		ParentEntity parent = parentRepository.findByUsername(username).get();
		if (parent == null) {
			return new ResponseEntity<RESTError>(new RESTError(400, "User not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ParentEntity>(parent, HttpStatus.OK);
	}// Radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/parent")
	public ResponseEntity<?> getAllParents() {
		List<ParentEntity> parent = (List<ParentEntity>) parentRepository.findAll();
		return new ResponseEntity<List<ParentEntity>>(parent, HttpStatus.OK);
	}// Radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/parentByStudentUsername/{studentUsername}")
	public ResponseEntity<?> getParentsByStudentUsername(@PathVariable String studentUsername) {
		StudentEntity student = studentRepository.findByUsername(studentUsername).get();
		if (student == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "User not found"), HttpStatus.NOT_FOUND);
		}
		List<ParentEntity> parent = parentRepository.findByStudentId(student.getId());
		return new ResponseEntity<List<ParentEntity>>(parent, HttpStatus.OK);
	}// Radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/student/{username}")
	public ResponseEntity<?> getStudent(@PathVariable String username) {
		StudentEntity student = studentRepository.findByUsername(username).get();
		if (student == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "User not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<StudentEntity>(student, HttpStatus.OK);
	}// radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/student")
	public ResponseEntity<?> getAllStudents() {
		List<StudentEntity> student = (List<StudentEntity>) studentRepository.findAll();
		return new ResponseEntity<List<StudentEntity>>(student, HttpStatus.OK);
	}// radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/subject/{name}")
	public ResponseEntity<?> getSubject(@PathVariable String name) {
		SubjectEntity subject = subjectRepository.findByName(name).get();
		if (subject == null) {
			return new ResponseEntity<RESTError>(new RESTError(400, "User not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
	}// radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/subject")
	public ResponseEntity<?> getAllSubjects() {
		List<SubjectEntity> subject = (List<SubjectEntity>) subjectRepository.findAll();
		return new ResponseEntity<List<SubjectEntity>>(subject, HttpStatus.OK);
	}// radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/grade/{id}")
	public ResponseEntity<?> getGrade(@PathVariable Integer id) {
		GradeRecordEntity grade = gradeRepository.findById(id).get();
		if (grade == null) {
			return new ResponseEntity<RESTError>(new RESTError(400, "User not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<GradeRecordEntity>(grade, HttpStatus.OK);
	}// Radi

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/grade")
	public ResponseEntity<?> getAllGrades() {
		List<GradeRecordEntity> grade = (List<GradeRecordEntity>) gradeRepository.findAll();
		return new ResponseEntity<List<GradeRecordEntity>>(grade, HttpStatus.OK);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}// radi
}