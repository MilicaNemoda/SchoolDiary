package com.iktpreobuka.schoolEDiary.controllers.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.schoolEDiary.entities.DTO.AdminDTO;
import com.iktpreobuka.schoolEDiary.entities.DTO.ParentDTO;
import com.iktpreobuka.schoolEDiary.entities.DTO.StudentDTO;
import com.iktpreobuka.schoolEDiary.entities.DTO.TeacherDTO;
import com.iktpreobuka.schoolEDiary.entities.DTO.UserDTO;

@Component
public class UserCustomValidator implements Validator {
	@Override
	public boolean supports(Class<?> myClass) {
		return UserDTO.class.isAssignableFrom(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDTO user = null;
		if (target instanceof StudentDTO) {
			user = (StudentDTO) target;
		} else if (target instanceof TeacherDTO) {
			user = (TeacherDTO) target;
		} else if (target instanceof ParentDTO) {
			user = (ParentDTO) target;
		} else if (target instanceof AdminDTO) {
			user = (AdminDTO) target;
		}

		if (!user.getPassword().equals(user.getRepeatedPassword())) {
			errors.reject("400", "Passwords must be the same");
		}
	}

}
