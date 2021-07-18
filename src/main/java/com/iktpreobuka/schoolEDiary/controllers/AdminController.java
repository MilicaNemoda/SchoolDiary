package com.iktpreobuka.schoolEDiary.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import com.iktpreobuka.schoolEDiary.services.FileHandler;

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
		if (!teacher.getPassword().equals(teacher.getRepeatedPassword())) {
			return new ResponseEntity<RESTError>(new RESTError(441, "Passwords not matching"), HttpStatus.FORBIDDEN);
		}
		
		int i = 0;
		Set<SubjectEntity> subjects = new HashSet<SubjectEntity>();
		while (teacher.getSubjects().get(i)!= null) {
			if (subjectRepository.findByName(teacher.getSubjects().get(i)).get() == null) {
				return new ResponseEntity<RESTError>(new RESTError(400, "Student not found."), HttpStatus.NOT_FOUND);
			}
			subjects.add(subjectRepository.findByName(teacher.getSubjects().get(i)).get());
			i++;
		}
		
		int j = 0;
		Set<SchoolClassEntity> schoolClass = new HashSet<SchoolClassEntity>();
		while (teacher.getClasses().get(j)!= null) {
			if (schoolClassRepository.findByName(teacher.getClasses().get(j)).get() == null) {
				return new ResponseEntity<RESTError>(new RESTError(400, "Student not found."), HttpStatus.NOT_FOUND);
			}
			schoolClass.add(schoolClassRepository.findByName(teacher.getClasses().get(j)).get());
			i++;
		}

		TeacherEntity newTeacher = new TeacherEntity();

		newTeacher.setUsername(teacher.getUsername());
		newTeacher.setEmail(teacher.getEmail());
		newTeacher.setPassword(teacher.getPassword());
		newTeacher.setFirstName(teacher.getFirstName());
		newTeacher.setLastName(teacher.getLastName());
		newTeacher.setLastName(teacher.getLastName());
		newTeacher.setLastName(teacher.getLastName());

		return new ResponseEntity<TeacherEntity>(teacherRepository.save(newTeacher), HttpStatus.OK);
	}
	// Da li u AdminController pisem POST za svaki tip usera posebno ili da napravim
	// jednu metodu koja ce moci da prepozna tip?

	//--POST Student
	
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

	//--POST parent
	
	@RequestMapping(method = RequestMethod.POST, value = "/parent")
	public ResponseEntity<?> addParent(@RequestBody ParentDTO parent) {
		int i = 0;
		Set<StudentEntity> children = new HashSet<StudentEntity>();
		while (parent.getChildUsername().get(i) != null) {
			if (studentRepository.findByUsername(parent.getChildUsername().get(i)).get() == null) {
				return new ResponseEntity<RESTError>(new RESTError(400, "Student not found."), HttpStatus.NOT_FOUND);
			}
			children.add(studentRepository.findByUsername(parent.getChildUsername().get(i)).get());
			i++;
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
	
	//--POST address

	@RequestMapping(method = RequestMethod.POST, value = "/address")
	public ResponseEntity<?> addAddress(@RequestBody AddressEntity newAddress) {

		if (addressRepository.findByStreet(newAddress.getStreet()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(440, "Address already created."), HttpStatus.FORBIDDEN);
		}
		// promeni ovo 444, i sta bi jos moglo da se doda od validacije?
		// proveri sta sve treba od validacije???!!!

		return new ResponseEntity<AddressEntity>(addressRepository.save(newAddress), HttpStatus.OK);
	}
	
	//--POST schoolYear

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
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/schoolYear{id}")
	public ResponseEntity<?> removeSchoolYear(@PathVariable Integer id) {
		SchoolYearEntity newSchoolYear = schoolYearRepository.findById(id).get(); 
//			
			return new ResponseEntity<SchoolYearEntity>(schoolYearRepository.save(newSchoolYear), HttpStatus.OK);

		}

	//--POST subject
	
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
	
	//TODO: match schoolClass - schoolYear, schoolClassStudent

	@RequestMapping(method = RequestMethod.PUT, value = "/teacher/{id}")
	public ResponseEntity<?> teacherUpdate(@RequestBody TeacherEntity updatedTeacher, @PathVariable Integer id) {
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
				teacherRepository.save(teacherEntity);
				return new ResponseEntity<TeacherEntity>(teacherEntity, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(400, "User not found"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(500, "Exception occurred:" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
			// Da li treba Exception svuda da stavljam? i da li je u redu ovo 500??
			// Kako da dodam listu predmeta i listu odeljenja??
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/student/{id}")
	public ResponseEntity<?> studentUpdate(@RequestBody StudentEntity updatedStudent, @PathVariable Integer id) {
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
//				if (updatedStudent.getDateOfBirth() != null) {
//					studentEntity.setDateOfBirth(updatedStudent.getDateOfBirth());
//				}
				studentRepository.save(studentEntity);
				return new ResponseEntity<StudentEntity>(studentEntity, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(400, "User not found"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(500, "Exception occurred:" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/parent/{id}")
	public ResponseEntity<?> parentUpdate(@RequestBody ParentEntity updatedParent, @PathVariable Integer id) {
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
				parentRepository.save(parentEntity);
				return new ResponseEntity<ParentEntity>(parentEntity, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(400, "User not found"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(500, "Exception occurred:" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} // Dodati Lisu djaka.
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

	@RequestMapping(method = RequestMethod.PUT, value = "/grade")
	public ResponseEntity<?> addGradeToStudent(@RequestBody GradeDTO grade) {
//			if (newGrade.getName().equals(null) || newGrade.getId().equals(null) || newGrade.getGradeType().equals(null)) {
//			return new ResponseEntity<RESTError>(new RESTError(400, "Grade parameters are not complete."),
//					HttpStatus.BAD_REQUEST);
//		}
		StudentEntity student = studentRepository.findByUsername(grade.getStudentUsername()).get();
		if (student == null) {
			return new ResponseEntity<RESTError>(new RESTError(400, "User not found"), HttpStatus.NOT_FOUND);
		}
		SubjectEntity subject = subjectRepository.findByName(grade.getSubjectName()).get();
		GradeRecordEntity newGrade = new GradeRecordEntity();
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
	// DELETE Predmeta Nastavnika Učenika Roditelja Ocena
}