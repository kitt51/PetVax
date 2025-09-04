package com.kitt51.PetVax.Pet.Command.Domain;

import java.time.LocalDate;

public record CreatePetCommand(
    long ownerId,
    String name,
    String type,
    String gender,
    String breed,
    LocalDate birthDate) {

    public static CreatePetCommand of(CreatePetRequest request){
        return new CreatePetCommand(
                request.ownerId(),
                request.name(),
                request.type(),
                request.gender(),
                request.breed(),
                request.birthDate()
        );
    }
}
