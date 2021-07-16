package com.iktpreobuka.schoolEDiary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.schoolEDiary.entities.TeacherEntity;

public interface TeacherRepository extends CrudRepository<TeacherEntity, Integer>  {
	TeacherEntity findByUsername(String userName);
}
