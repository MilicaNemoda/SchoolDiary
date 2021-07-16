package com.iktpreobuka.schoolEDiary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.schoolEDiary.controllers.utils.RESTError;
import com.iktpreobuka.schoolEDiary.entities.AddressEntity;
import com.iktpreobuka.schoolEDiary.entities.GradeEntity;
import com.iktpreobuka.schoolEDiary.entities.ParentEntity;
import com.iktpreobuka.schoolEDiary.entities.StudentEntity;
import com.iktpreobuka.schoolEDiary.entities.SubjectEntity;
import com.iktpreobuka.schoolEDiary.entities.TeacherEntity;
import com.iktpreobuka.schoolEDiary.entities.UserEntity;
import com.iktpreobuka.schoolEDiary.entities.DTO.ParentDTO;
import com.iktpreobuka.schoolEDiary.entities.DTO.StudentDTO;
import com.iktpreobuka.schoolEDiary.entities.DTO.TeacherDTO;
import com.iktpreobuka.schoolEDiary.entities.DTO.UserDTO;
import com.iktpreobuka.schoolEDiary.repositories.AddressRepository;
import com.iktpreobuka.schoolEDiary.repositories.GradeRepository;
import com.iktpreobuka.schoolEDiary.repositories.ParentRepository;
import com.iktpreobuka.schoolEDiary.repositories.StudentRepository;
import com.iktpreobuka.schoolEDiary.repositories.SubjectRepository;
import com.iktpreobuka.schoolEDiary.repositories.TeacherRepository;
import com.iktpreobuka.schoolEDiary.repositories.UserRepository;

@RestController
@RequestMapping(value = "/api/v1/schoolEDiary/admin")//zasto bese api?
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
	
//	@RequestMapping(method = RequestMethod.POST, value = "/user")
//	public ResponseEntity<?> addTeacher(@RequestBody UserDTO user) {
//
//		// perform check if same username is used in database !subjectRepository.findByName(newSubject.getName()).equals(null)
//		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
//			return new ResponseEntity<RESTError>(new RESTError(444, "Username already used, choose another."),
//					HttpStatus.FORBIDDEN);
//		}
//
//		// check for matching password and repeatedPassword
//		if (!user.getPassword().equals(user.getRepeatedPassword())) {
//			return new ResponseEntity<RESTError>(new RESTError(555, "Passwords not matching"), HttpStatus.FORBIDDEN);
//		}
//
//		UserEntity newUser = new TeacherEntity();
//
//		newUser.setUsername(user.getUsername());
//		newUser.setEmail(user.getEmail());
//		newUser.setPassword(user.getPassword());
//		newUser.setFirstName(user.getFirstName());
//		newUser.setLastName(user.getLastName());
//		newUser.setDateOfBirth(user.getDateOfBirth());
//		
//	return new ResponseEntity<UserEntity>(userRepository.save(newUser), HttpStatus.OK);
//	}

	@RequestMapping(method = RequestMethod.POST, value = "/teacher")
	public ResponseEntity<?> addTeacher(@RequestBody TeacherDTO teacher) {

		// perform check if same username is used in database !subjectRepository.findByName(newSubject.getName()).equals(null)
		if (!teacherRepository.findByUsername(teacher.getUsername()).equals(null)) {
			return new ResponseEntity<RESTError>(new RESTError(444, "Username already used, choose another."),
					HttpStatus.FORBIDDEN);
		}

		// check for matching password and repeatedPassword
		if (!teacher.getPassword().equals(teacher.getRepeatedPassword())) {
			return new ResponseEntity<RESTError>(new RESTError(555, "Passwords not matching"), HttpStatus.FORBIDDEN);
		}

		TeacherEntity newTeacher = new TeacherEntity();

		newTeacher.setUsername(teacher.getUsername());
		newTeacher.setEmail(teacher.getEmail());
		newTeacher.setPassword(teacher.getPassword());
		newTeacher.setFirstName(teacher.getFirstName());
		newTeacher.setLastName(teacher.getLastName());
		
	return new ResponseEntity<TeacherEntity>(teacherRepository.save(newTeacher), HttpStatus.OK);
	} 
	//Da li u AdminController pisem POST za svaki tip usera posebno ili da napravim jednu metodu koja ce moci da prepozna tip?
	
	@RequestMapping(method = RequestMethod.POST, value = "/student")
	public ResponseEntity<?> addStudent(@RequestBody StudentDTO student) {

		// perform check if same username is used in database !subjectRepository.findByName(newSubject.getName()).equals(null)
		if (studentRepository.findByUsername(student.getUsername()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(444, "Username already used, choose another."),
					HttpStatus.FORBIDDEN);
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
		newStudent.setDateOfBirth(student.getDateOfBirth());
		
	return new ResponseEntity<StudentEntity>(studentRepository.save(newStudent), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/parent")
	public ResponseEntity<?> addParent(@RequestBody ParentDTO parent) {

		// perform check if same username is used in database !subjectRepository.findByName(newSubject.getName()).equals(null)
		if (!parentRepository.findByUsername(parent.getUsername()).equals(null)) {
			return new ResponseEntity<RESTError>(new RESTError(444, "Username already used, choose another."),
					HttpStatus.FORBIDDEN);
		}

		// check for matching password and repeatedPassword
		if (!parent.getPassword().equals(parent.getRepeatedPassword())) {
			return new ResponseEntity<RESTError>(new RESTError(555, "Passwords not matching"), HttpStatus.FORBIDDEN);
		}

		ParentEntity newParent = new ParentEntity();

		newParent.setUsername(parent.getUsername());
		newParent.setEmail(parent.getEmail());
		newParent.setPassword(parent.getPassword());
		newParent.setFirstName(parent.getFirstName());
		newParent.setLastName(parent.getLastName());
		
	return new ResponseEntity<ParentEntity>(parentRepository.save(newParent), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/address")
	public ResponseEntity<?> addAddress(@RequestBody AddressEntity newAddress) {

		if (addressRepository.findByStreet(newAddress.getStreet()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(444, "Address already created."), HttpStatus.FORBIDDEN);
		}
		// promeni ovo 444, i sta bi jos moglo da se doda od validacije?
		// proveri sta sve treba od validacije???!!!

		return new ResponseEntity<AddressEntity>(addressRepository.save(newAddress), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/subject")
	public ResponseEntity<?> addSubject(@RequestBody SubjectEntity newSubject) {

		if (!subjectRepository.findByName(newSubject.getName()).equals(null)) {
			return new ResponseEntity<RESTError>(new RESTError(444, "Subject already created."), HttpStatus.FORBIDDEN);
		}
		// promeni ovo 444, i sta bi jos moglo da se doda od validacije?
		// proveri sta sve treba od validacije???!!!

		return new ResponseEntity<SubjectEntity>(subjectRepository.save(newSubject), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/grade")
	public ResponseEntity<?> addGrade(@RequestBody GradeEntity newGrade) {
		if (newGrade.getName().equals(null) || newGrade.getId().equals(null) || newGrade.getGradeType().equals(null)) {
			return new ResponseEntity<RESTError>(new RESTError(400, "Grade parameters are not complete."),
					HttpStatus.BAD_REQUEST);
		}
		// promeni ovo 400, i sta bi jos moglo da se doda od validacije?
		// proveri sta sve treba od validacije???!!!

		return new ResponseEntity<GradeEntity>(gradeRepository.save(newGrade), HttpStatus.OK);

	}

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
				if (updatedStudent.getDateOfBirth() != null) {
					studentEntity.setDateOfBirth(updatedStudent.getDateOfBirth());
				}
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

	@RequestMapping(method = RequestMethod.PUT, value = "/{user_id}/addressId")
	public ResponseEntity<?> addAddressToAUser(@PathVariable Integer id, @RequestParam Integer adrId) {
		UserEntity user = userRepository.findById(id).get();
		AddressEntity address = addressRepository.findById(adrId).get();
		user.setAddress(address);
		userRepository.save(user); 
		return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
		// automatski ce biti sacuvana i adresa/		return user;//prepravljeno sa slajda 102
	}
	// GET Predmeta Nastavnika Učenika Roditelja Ocena
	// DELETE Predmeta Nastavnika Učenika Roditelja Ocena
}