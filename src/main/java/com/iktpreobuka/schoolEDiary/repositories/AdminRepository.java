package com.iktpreobuka.schoolEDiary.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.schoolEDiary.entities.AdminEntity;

public interface AdminRepository extends CrudRepository<AdminEntity, Integer> {

	Optional<AdminEntity> findByUsername(String username);

}
