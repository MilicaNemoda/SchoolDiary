package com.iktpreobuka.schoolEDiary.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.schoolEDiary.entities.GradeEntity;

@Repository
public interface GradeRepository extends CrudRepository<GradeEntity, Integer> {

}
