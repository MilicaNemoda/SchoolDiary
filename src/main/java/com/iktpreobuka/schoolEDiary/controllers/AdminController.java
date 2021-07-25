package com.iktpreobuka.schoolEDiary.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

//	@Autowired
//	private FileHandler fileHandler;
//	
//// TODO: POST List of users and other entities 
//	@RequestMapping(method = RequestMethod.POST, path = "/userListUpload")
//	public String userListUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
//		
//		return fileHandler.userListUpload(file, redirectAttributes);
//	}

// -- POST teacher
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/teacher")
	public ResponseEntity<?> addTeacher(@RequestBody TeacherDTO teacher) {

		// perform check if same username is used in database
		// !subjectRepository.findByName(newSubject.getName()).equals(null)
		if (teacherRepository.findByUsername(teacher.getUsername()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(440, "Username already used, choose another."),
					HttpStatus.FORBIDDEN);
		}
		// TODO: proveri da li pasvord postoji?!

		// check for matching password and repeatedPassword
		if (teacher.getPassword().equals(teacher.getRepeatedPassword())) {
			return new ResponseEntity<RESTError>(new RESTError(441, "Passwords not matching"), HttpStatus.FORBIDDEN);
		}

		Set<SubjectEntity> subjects = new HashSet<SubjectEntity>();
		for (String subject : teacher.getSubjects()) {
			if (subjectRepository.findByName(subject).get() == null) {
				return new ResponseEntity<RESTError>(new RESTError(400, "Student not found."), HttpStatus.NOT_FOUND);
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
		newTeacher.setPassword(teacher.getPassword());
		newTeacher.setFirstName(teacher.getFirstName());
		newTeacher.setLastName(teacher.getLastName());
		newTeacher.setSubjects(subjects);
		newTeacher.setClasses(schoolClasses);

		return new ResponseEntity<TeacherEntity>(teacherRepository.save(newTeacher), HttpStatus.OK);
	}

	// --POST Student

	@RequestMapping(method = RequestMethod.POST, value = "/student")
	public ResponseEntity<?> addStudent(@RequestBody StudentDTO student) {

		if (studentRepository.findByUsername(student.getUsername()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(444, "Username already used, choose another."),
					HttpStatus.FORBIDDEN);
		}

		SchoolYearEntity schoolYear = schoolYearRepository.findByYear(student.getYear());
		if (schoolYear == null) {
			return new ResponseEntity<RESTError>(new RESTError(400, "Wrong school year."), HttpStatus.BAD_REQUEST);
		}

		SchoolClassEntity schoolClass = schoolClassRepository.findByName(student.getSchoolClass()).get();
		if (schoolClass == null) {
			return new ResponseEntity<RESTError>(new RESTError(400, "Wrong school class."), HttpStatus.BAD_REQUEST);
		}
		
		if (schoolYear.getYear() != (schoolClass.getName()).charAt(0))
		// TODO: proveri da li pasvord postoji?!
		// check for matching password and repeatedPassword
		if (!student.getPassword().equals(student.getRepeatedPassword())) {
			return new ResponseEntity<RESTError>(new RESTError(555, "Passwords not matching"), HttpStatus.FORBIDDEN);
		}

		StudentEntity newStudent = new StudentEntity();

		newStudent.setUsername(student.getUsername());
		newStudent.setEmail(student.getEmail());
		newStudent.setPassword(student.getPassword());
		newStudent.setFirstName(student.getFirstName());
		newStudent.setLastName(student.getLastName());
		newStudent.setSchoolYear(schoolYear);
		newStudent.setSchoolClass(schoolClass);
		return new ResponseEntity<StudentEntity>(studentRepository.save(newStudent), HttpStatus.OK);
	}

	// --POST parent

	@RequestMapping(method = RequestMethod.POST, value = "/parent")
	public ResponseEntity<?> addParent(@RequestBody ParentDTO parent) {

		Set<StudentEntity> children = new HashSet<StudentEntity>();
		for (String childUsername : parent.getChildUsernames()) {
			if (studentRepository.findByUsername(childUsername).get() == null) {
				return new ResponseEntity<RESTError>(new RESTError(400, "Student not found."), HttpStatus.NOT_FOUND);
				// TODO anotacija za validaciju null
				// TODO: proveri da li pasvord postoji?!
			}
			children.add(studentRepository.findByUsername(childUsername).get());
		}

		ParentEntity newParent = new ParentEntity();
		newParent.setUsername(parent.getUsername());
		newParent.setEmail(parent.getEmail());
		newParent.setPassword(parent.getPassword());
		newParent.setFirstName(parent.getFirstName());
		newParent.setLastName(parent.getLastName());
		newParent.setStudent(children);
		parentRepository.save(newParent);
		return new ResponseEntity<ParentEntity>(newParent, HttpStatus.OK);
	}

	// --POST address

	@RequestMapping(method = RequestMethod.POST, value = "/address")
	public ResponseEntity<?> addAddress(@RequestBody AddressEntity newAddress) {

		if (addressRepository.findByStreet(newAddress.getStreet()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(440, "Address already created."), HttpStatus.FORBIDDEN);
		}
		// promeni ovo 444, i sta bi jos moglo da se doda od validacije?
		// proveri sta sve treba od validacije???!!!

		return new ResponseEntity<AddressEntity>(addressRepository.save(newAddress), HttpStatus.OK);
	}

	// --POST schoolYear

	@RequestMapping(method = RequestMethod.POST, value = "/schoolYear")
	public ResponseEntity<?> addSchoolYear(@RequestBody SchoolYearEntity newSchoolYear) {
//			if (newSchoolYear.getName().equals(null) || newSchoolYear.getId().equals(null) || newSchoolYear.getGradeType().equals(null)) {
//				return new ResponseEntity<RESTError>(new RESTError(400, "Grade parameters are not complete."),
//						HttpStatus.BAD_REQUEST);
//			}
		// promeni ovo 400, i sta bi jos moglo da se doda od validacije?
		// proveri sta sve treba od validacije???!!!

		return new ResponseEntity<SchoolYearEntity>(schoolYearRepository.save(newSchoolYear), HttpStatus.OK);

	}
	// --POST subject

	@RequestMapping(method = RequestMethod.POST, value = "/subject")
	public ResponseEntity<?> addSubject(@RequestBody SubjectDTO subject) {

		if (subjectRepository.findByName(subject.getName()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(440, "Subject already created."), HttpStatus.FORBIDDEN);
		}
		// promeni ovo 444, i sta bi jos moglo da se doda od validacije?
		// proveri sta sve treba od validacije???!!!

		SchoolYearEntity schoolYear = schoolYearRepository.findByYear(subject.getYear());
		if (schoolYear == null) {
			return new ResponseEntity<RESTError>(new RESTError(400, "Wrong school year."), HttpStatus.BAD_REQUEST);
		}

		SubjectEntity newSubject = new SubjectEntity();
		newSubject.setName(subject.getName());
		newSubject.setWeeklyNumberOfLectures(subject.getWeeklyNumberOfLectures());
		newSubject.setSchoolYear(schoolYear);

		return new ResponseEntity<SubjectEntity>(subjectRepository.save(newSubject), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/schoolClass")
	public ResponseEntity<?> addClassRoom(@RequestBody SchoolClassEntity newSchoolClass) {
//		if (newGrade.getName().equals(null) || newGrade.getId().equals(null) || newGrade.getGradeType().equals(null)) {
//			return new ResponseEntity<RESTError>(new RESTError(400, "Grade parameters are not complete."),
//					HttpStatus.BAD_REQUEST);
//		}
//		// promeni ovo 400, i sta bi jos moglo da se doda od validacije?
//		// proveri sta sve treba od validacije???!!!

		return new ResponseEntity<SchoolClassEntity>(schoolClassRepository.save(newSchoolClass), HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/schoolYear/{id}")
	public ResponseEntity<?> removeSchoolYear(@PathVariable Integer id) {
		SchoolYearEntity schoolYear = schoolYearRepository.findById(id).get();
		schoolYearRepository.delete(schoolYear);
		return new ResponseEntity<SchoolYearEntity>(schoolYear, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/teacher/{id}")
	public ResponseEntity<?> removeTeracher(@PathVariable String username) {
		TeacherEntity deletedTeacher = teacherRepository.findByUsername(username).get();
		if (deletedTeacher == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Student not found."), HttpStatus.NOT_FOUND);
		}
		teacherDAOImpl.removeTeacherEntity(username);
		return new ResponseEntity<TeacherEntity>(deletedTeacher, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/parent/{id}")
	public ResponseEntity<?> removeParent(@PathVariable String username) {
		ParentEntity parent = parentRepository.findByUsername(username).get();
		if (parent == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Parent not found."), HttpStatus.NOT_FOUND);
		}
		parentRepository.delete(parent);// Radi ali ne ispisuje sta je obriso i teba provera da li taj roditelj
										// postoji!!
		return new ResponseEntity<ParentEntity>(parent, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/student/{username}")
	public ResponseEntity<?> removeStudent(@PathVariable String username) {
		StudentEntity student = studentRepository.findByUsername(username).get();
		if (student == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Student not found."), HttpStatus.NOT_FOUND);
		}
		studentDAOImpl.removeStudentEntity(student.getId());
		return new ResponseEntity<StudentEntity>(student, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/subject/{id}")
	public ResponseEntity<?> removeSubject(@PathVariable String subjectName) {
		SubjectEntity subject = subjectRepository.findByName(subjectName).get();
		if (subject == null) {
			return new ResponseEntity<RESTError>(new RESTError(404, "Subject not found."), HttpStatus.NOT_FOUND);
		}
		subjectRepository.delete(subject);
		return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
	}

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
	@RequestMapping(method = RequestMethod.PUT, value = "/parentsChildren/{parentId}")
	public ResponseEntity<?> addStudentsToParent(@RequestBody List<String> childrenUsernames,
			@PathVariable Integer parentId) {
		ParentEntity parent = parentRepository.findById(parentId).get();
		Set<StudentEntity> children = new HashSet<StudentEntity>();
		for (String childUsername : childrenUsernames) {
			if (studentRepository.findByUsername(childUsername).get() == null) {
				return new ResponseEntity<RESTError>(new RESTError(400, "Student not found."), HttpStatus.NOT_FOUND);
				// TODO anotacija za validaciju null
			}
			children.add(studentRepository.findByUsername(childUsername).get());
		}

		return new ResponseEntity<Set<StudentEntity>>(children, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/teacher/{id}")
	public ResponseEntity<?> teacherUpdate(@RequestBody TeacherDTO updatedTeacher, @PathVariable Integer id) {
		try {
			if (teacherRepository.existsById(updatedTeacher.getId())) {
				TeacherEntity teacherEntity = teacherRepository.findById(updatedTeacher.getId()).get();
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
				}
				if (!updatedTeacher.getPassword().equals(updatedTeacher.getRepeatedPassword())) {
					return new ResponseEntity<RESTError>(new RESTError(441, "Passwords not matching"),
							HttpStatus.FORBIDDEN);
				}
				if (updatedTeacher.getPassword() != null) {
					teacherEntity.setPassword(updatedTeacher.getPassword());

				}

				teacherRepository.save(teacherEntity);
				return new ResponseEntity<TeacherEntity>(teacherEntity, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(400, "Teacher not found"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(500, "Exception occurred:" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/student/{id}")
	public ResponseEntity<?> studentUpdate(@RequestBody StudentDTO updatedStudent, @PathVariable Integer id) {
		try {
			if (teacherRepository.existsById(updatedStudent.getId())) {
				StudentEntity studentEntity = studentRepository.findById(updatedStudent.getId()).get();
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
					studentEntity.setSchoolYear(schoolYearRepository.findByYear(updatedStudent.getYear()));
				}
				if (updatedStudent.getSchoolClass() != null) {
					studentEntity
							.setSchoolClass(schoolClassRepository.findByName(updatedStudent.getSchoolClass()).get());
				}
				if (!updatedStudent.getPassword().equals(updatedStudent.getRepeatedPassword())) {
					return new ResponseEntity<RESTError>(new RESTError(441, "Passwords not matching"),
							HttpStatus.FORBIDDEN);
				}
				if (updatedStudent.getPassword() != null) {
					studentEntity.setPassword(updatedStudent.getPassword());
				}

				studentRepository.save(studentEntity);
				return new ResponseEntity<StudentEntity>(studentEntity, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(400, "Student not found"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(500, "Exception occurred:" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/parent/{id}")
	public ResponseEntity<?> parentUpdate(@RequestBody ParentDTO updatedParent, @PathVariable Integer id) {
		try {
			if (teacherRepository.existsById(updatedParent.getId())) {
				ParentEntity parentEntity = parentRepository.findById(updatedParent.getId()).get();
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
				}
				
				if (!updatedParent.getPassword().equals(updatedParent.getRepeatedPassword())) {
					return new ResponseEntity<RESTError>(new RESTError(441, "Passwords not matching"),
							HttpStatus.FORBIDDEN);
				}
				if (updatedParent.getPassword() != null) {
					parentEntity.setPassword(updatedParent.getPassword());
				}

				parentRepository.save(parentEntity);
				return new ResponseEntity<ParentEntity>(parentEntity, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(400, "User not found"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(500, "Exception occurred:" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/changePassword/{id}")
	public ResponseEntity<?> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword,
			@PathVariable Integer id) {

		UserEntity user = userRepository.findById(id).get();

		if (user == null)
			return new ResponseEntity<RESTError>(new RESTError(400, "User not found"), HttpStatus.NOT_FOUND);

		if (user.getPassword().equals(oldPassword))
			user.setPassword(newPassword);

		userRepository.save(user);
		return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
	}

	// TODO: PUT Povezivanje studenta sa roditeljima,
	// profesor i predmet, ucenik i odeljenje, ucenik i razred

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

	@RequestMapping(method = RequestMethod.POST, value = "/grade")
	public ResponseEntity<?> addGradeToStudent(@RequestBody GradeDTO grade) {
//			if (newGrade.getName().equals(null) || newGrade.getId().equals(null) || newGrade.getGradeType().equals(null)) {
//			return new ResponseEntity<RESTError>(new RESTError(400, "Grade parameters are not complete."),
//					HttpStatus.BAD_REQUEST);
//		}
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
		// TODO validacija
		GradeRecordEntity newGrade = new GradeRecordEntity();
		newGrade.setGradeType(grade.getGradeType());
		newGrade.setName(grade.getName());
		newGrade.setStudentGrade(student);
		newGrade.setSubjectGrade(subject);
		gradeRepository.save(newGrade);
		return new ResponseEntity<GradeRecordEntity>(newGrade, HttpStatus.OK);
		// automatski ce biti sacuvana i adresa/ return user;//prepravljeno sa slajda
		// 102
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

	@RequestMapping(method = RequestMethod.GET, value = "/teacher/{username}")
	public ResponseEntity<?> getTeracher(@PathVariable String username) {
		TeacherEntity teacher = teacherRepository.findByUsername(username).get();
		return new ResponseEntity<TeacherEntity>(teacher, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/teacher")
	public ResponseEntity<?> getAllTerachers() {
		List<TeacherEntity> teacher = (List<TeacherEntity>) teacherRepository.findAll();
		return (ResponseEntity<?>) teacher;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/parent/{username}")
	public ResponseEntity<?> getParent(@PathVariable String username) {
		ParentEntity parent = parentRepository.findByUsername(username).get();// vidi da li treba da bude optional u
		// parentRepository
		return new ResponseEntity<ParentEntity>(parent, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/parent")
	public ResponseEntity<?> getAllParents() {
		List<ParentEntity> parent = (List<ParentEntity>) parentRepository.findAll();
		return (ResponseEntity<?>) parent;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/student/{username}")
	public ResponseEntity<?> getStudent(@PathVariable String username) {
		StudentEntity student = studentRepository.findByUsername(username).get();
		studentRepository.delete(student);
		return new ResponseEntity<StudentEntity>(student, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/student")
	public ResponseEntity<?> getAllStudents() {
		List<StudentEntity> student = (List<StudentEntity>) studentRepository.findAll();
		return (ResponseEntity<?>) student;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/subject/{name}")
	public ResponseEntity<?> getSubject(@PathVariable String name) {
		SubjectEntity subject = subjectRepository.findByName(name).get();
		return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/subject")
	public ResponseEntity<?> getAllSubjects(@PathVariable Integer id) {
		SubjectEntity subject = subjectRepository.findById(id).get();
		subjectRepository.delete(subject);
		return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/grade/{id}")
	public ResponseEntity<?> getGrade(@PathVariable Integer id) {
		GradeRecordEntity grade = gradeRepository.findById(id).get();
		gradeRepository.delete(grade);
		return new ResponseEntity<GradeRecordEntity>(grade, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/grade")
	public ResponseEntity<?> getAllGrades(@PathVariable Integer id) {
		GradeRecordEntity grade = gradeRepository.findById(id).get();
		gradeRepository.delete(grade);
		return new ResponseEntity<GradeRecordEntity>(grade, HttpStatus.OK);
	}
}