package com.iktpreobuka.schoolEDiary.repositories;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.schoolEDiary.entities.SubjectEntity;

public interface SubjectRepository extends CrudRepository<SubjectEntity, Integer> {
	Optional<SubjectEntity> findByName (String name);
}
