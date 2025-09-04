package com.kitt51.PetVax.Pet.Command.Domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetCommandRepository extends CrudRepository<Pet,Long> {

}
