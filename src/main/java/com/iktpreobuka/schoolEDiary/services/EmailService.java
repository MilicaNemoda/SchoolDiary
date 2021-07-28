package com.iktpreobuka.schoolEDiary.services;

import com.iktpreobuka.schoolEDiary.models.EmailObject;

public interface EmailService {
	
	void sendSimpleMessage (EmailObject object);
}
