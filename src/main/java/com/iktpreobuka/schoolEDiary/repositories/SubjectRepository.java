package com.iktpreobuka.schoolEDiary.repositories;


import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.schoolEDiary.entities.SubjectEntity;

public interface SubjectRepository extends CrudRepository<SubjectEntity, Integer> {
	SubjectEntity findByName (String name);
}
