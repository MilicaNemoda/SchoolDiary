package com.iktpreobuka.schoolEDiary.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.schoolEDiary.entities.SchoolYearEntity;

public interface SchoolYearRepository extends CrudRepository<SchoolYearEntity, Integer> {
	SchoolYearEntity findByYear(Integer year);

}
