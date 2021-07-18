package com.iktpreobuka.schoolEDiary.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.schoolEDiary.entities.GradeRecordEntity;

@Repository
public interface GradeRepository extends CrudRepository<GradeRecordEntity, Integer> {

}
