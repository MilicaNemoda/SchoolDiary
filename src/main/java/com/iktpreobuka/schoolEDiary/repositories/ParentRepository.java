package com.iktpreobuka.schoolEDiary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.schoolEDiary.entities.ParentEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Integer> {
	ParentEntity findByUsername(String username);
}
