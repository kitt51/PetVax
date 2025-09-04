package com.kitt51.PetVax.Pet.Command.Domain;

import java.time.LocalDate;

public record UpdatePetCommand(
        long petId,
        String name,
        String type,
        String gender,
        String breed,
        LocalDate birthDate) {

    public static UpdatePetCommand of(long id,UpdatePetRequest request){
        return new UpdatePetCommand(
                id,
                request.name(),
                request.type(),
                request.gender(),
                request.breed(),
                request.birthDate()
        );
    }
}
