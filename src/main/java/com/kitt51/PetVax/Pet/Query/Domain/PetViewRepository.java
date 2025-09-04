package com.kitt51.PetVax.Pet.Query.Domain;

import com.kitt51.PetVax.Pet.Command.Domain.Pet;
import com.kitt51.PetVax.Pet.PetDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetViewRepository extends MongoRepository<PetView,Long> {
   //List<PetDTO> findByOwnerId(Long id);
}
