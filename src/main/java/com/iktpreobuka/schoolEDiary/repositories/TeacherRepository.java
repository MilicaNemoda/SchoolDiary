package com.iktpreobuka.schoolEDiary.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.schoolEDiary.entities.TeacherEntity;

public interface TeacherRepository extends CrudRepository<TeacherEntity, Integer>  {
	Optional <TeacherEntity> findByUsername(String userName);
}
