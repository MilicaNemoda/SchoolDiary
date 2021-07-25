package com.iktpreobuka.schoolEDiary.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.schoolEDiary.entities.ParentEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Integer> {
	Optional <ParentEntity> findByUsername(String username);
}
