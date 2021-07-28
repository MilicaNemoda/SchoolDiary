package com.iktpreobuka.schoolEDiary.controllers.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.schoolEDiary.entities.DTO.UserDTO;

@Component
public class UserCustomValidator implements Validator {
	@Override
	public boolean supports(Class<?> myClass) {
		return UserDTO.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDTO user = (UserDTO) target;
		if (!user.getPassword().equals(user.getRepeatedPassword())) {
			errors.reject("400", "Passwords must be the same");
		}
	}
	
	//TODO Naprav custom validator za proveru da li user postoji/ i da li odeljenje dodeljeno studentu odgovara godini.
}
