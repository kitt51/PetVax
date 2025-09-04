package com.kitt51.PetVax.Pet.Query.Service;

import com.kitt51.PetVax.Pet.Command.Domain.Pet;
import com.kitt51.PetVax.Pet.PetDTO;
import com.kitt51.PetVax.Pet.PetInternalAPI;
import com.kitt51.PetVax.Pet.Query.Domain.PetView;
import com.kitt51.PetVax.Pet.Query.Domain.PetViewRepository;
import com.kitt51.PetVax.Exceptions.PetViewIdNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PetQueryService implements PetInternalAPI {
    private final PetViewRepository queryRepository;

    public PetQueryService(PetViewRepository queryRepository) {
        this.queryRepository = queryRepository;
    }
    @Override
    public Optional<PetDTO> findPetById(Long id){
        return Optional.of(PetDTO.of(queryRepository.findById(id).
                orElseThrow(() -> PetViewIdNotFoundException.of(id))));
    }
    public boolean isPetOwnedByUser(Long petId,Long ownerId){
       PetView pet = queryRepository.findById(petId)
               .orElseThrow(()->PetViewIdNotFoundException.of(petId));
       return pet.getOwnerId() == ownerId;
    }


}
