package com.iktpreobuka.schoolEDiary.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.schoolEDiary.entities.UserEntity;

public interface UserRepository extends CrudRepository <UserEntity, Integer> {
	Optional <UserEntity> findByUsername(String username);
}
