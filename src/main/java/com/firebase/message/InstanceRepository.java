package com.firebase.message;// CRUD refers Create, Read, Update, Delete

import com.firebase.message.repository.InstanceDto;
import org.springframework.data.repository.CrudRepository;

public interface InstanceRepository extends CrudRepository<InstanceDto, Long> {

}