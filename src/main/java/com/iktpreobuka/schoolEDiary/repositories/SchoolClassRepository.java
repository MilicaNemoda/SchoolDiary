package com.iktpreobuka.schoolEDiary.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.schoolEDiary.entities.SchoolClassEntity;

public interface SchoolClassRepository extends CrudRepository<SchoolClassEntity, Integer> {
	Optional<SchoolClassEntity>findByName(String name); 
}
