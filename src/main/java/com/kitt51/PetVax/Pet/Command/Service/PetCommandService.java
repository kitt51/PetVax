package com.kitt51.PetVax.Pet.Command.Service;

import com.kitt51.PetVax.Pet.Command.Domain.CreatePetCommand;
import com.kitt51.PetVax.Pet.Command.Domain.Pet;
import com.kitt51.PetVax.Pet.Command.Domain.PetCommandRepository;
import com.kitt51.PetVax.Pet.Command.Domain.UpdatePetCommand;
import com.kitt51.PetVax.Pet.Command.Events.PetCreatedEvent;
import com.kitt51.PetVax.Pet.Command.Events.PetUpdatedEvent;
import com.kitt51.PetVax.Exceptions.PetCommandIdNotFoundException;
import com.kitt51.PetVax.Pet.PetDTO;
import com.kitt51.PetVax.Pet.PetExternalAPI;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PetCommandService implements PetExternalAPI {

    private final PetCommandRepository petRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PetCommandService(PetCommandRepository petRepository, ApplicationEventPublisher eventPublisher) {
        this.petRepository = petRepository;
        this.eventPublisher = eventPublisher;
    }


    public PetDTO addPet(CreatePetCommand cmd){
        Pet pet = Pet.builder()
                .ownerId(cmd.ownerId())
                .name(cmd.name())
                .type(cmd.type())
                .gender(cmd.gender())
                .breed(cmd.breed())
                .birthDate(cmd.birthDate()).build();

        Pet savedPet = petRepository.save(pet);
        eventPublisher.publishEvent(
                new PetCreatedEvent(savedPet.getId(),savedPet.getOwnerId(),
                        savedPet.getName(),savedPet.getType(),savedPet.getGender(),
                        savedPet.getBreed(),savedPet.getBirthDate()));
        return PetDTO.of(pet);

    }

    @Override
    public PetDTO updatePet(UpdatePetCommand cmd) {

        Optional<Pet> existingPet = petRepository.findById(cmd.petId());
        if(existingPet.isEmpty()){
            throw PetCommandIdNotFoundException.of(cmd.petId());
        }
        Pet updatedPet = existingPet.get();
        updatedPet.setName(cmd.name());
        updatedPet.setType(cmd.type());
        updatedPet.setGender(cmd.gender());
        updatedPet.setBreed(cmd.breed());
        updatedPet.setBirthDate(cmd.birthDate());
        Pet savedPet = petRepository.save(updatedPet);
        eventPublisher.publishEvent(
                new PetUpdatedEvent(savedPet.getId(),savedPet.getOwnerId(),
                        savedPet.getName(),savedPet.getType(),savedPet.getGender(),
                        savedPet.getBreed(),savedPet.getBirthDate()));
        return PetDTO.of(savedPet);

    }



}
