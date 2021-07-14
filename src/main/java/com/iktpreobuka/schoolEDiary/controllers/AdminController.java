package com.iktpreobuka.schoolEDiary.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.schoolEDiary.entities.UserEntity;
import com.iktpreobuka.schoolEDiary.entities.DTO.UserDTO;

@RestController
@RequestMapping(value = "/project/users")
public class AdminController {
	
//	@RequestMapping(method = RequestMethod.POST, value = "")
//	public ResponseEntity<?> addUser(@RequestBody UserDTO user) {
//
//		// perform check if same username is used in database
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
//		UserEntity newUser = new UserEntity();
//
//		newUser.setUsername(user.getUsername());
//		newUser.setEmail(user.getEmail());
//		newUser.setPassword(user.getPassword());
//		newUser.setFirstName(user.getFirstName());
//		newUser.setLastName(user.getLastName());
//		newUser.setUserRole(ERole.ROLE_CUSTOMER);
//
//		return new ResponseEntity<UserEntity>(userRepository.save(newUser), HttpStatus.OK);
//	} Da li u AdminController pisem POST za svaki tip usera posebno ili da napravim jednu metodu koja ce moci da prepozna tip?
//	@RequestMapping(method = RequestMethod.POST)
//	public UserEntity addNewUser(@RequestBody UserDTO user) {
//		if (newUser.getUserRole().equals(UserRole.ROLE_SELLER)) {
//			userRepository.save(newUser);
//			
//			UserEntity newUser = new UserEntity();
//			
//			return newUser;
//		}
//		return null;
//	}
}
