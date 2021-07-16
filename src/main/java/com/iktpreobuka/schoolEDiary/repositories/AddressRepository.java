package com.iktpreobuka.schoolEDiary.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.schoolEDiary.entities.AddressEntity;

@Repository
public interface AddressRepository extends CrudRepository <AddressEntity, Integer> {
	Optional <AddressEntity> findByStreet (String address);
}
