package com.iktpreobuka.schoolEDiary.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.schoolEDiary.entities.ParentEntity;
import com.iktpreobuka.schoolEDiary.entities.StudentEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Integer> {
	Optional <ParentEntity> findByUsername(String username);
	List <ParentEntity> findByStudentId(Integer studentId);
}
