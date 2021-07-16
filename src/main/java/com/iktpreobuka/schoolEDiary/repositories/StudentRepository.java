package com.iktpreobuka.schoolEDiary.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.schoolEDiary.entities.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {

	Optional <StudentEntity> findByUsername(String username);
}
