package com.kitt51.PetVax.Pet;

import com.kitt51.PetVax.Pet.Command.Domain.Pet;
import com.kitt51.PetVax.Pet.Query.Domain.PetView;

import java.time.LocalDate;

public record PetDTO(
        String name,
        String type,
        String gender,
        String breed,
        LocalDate birthDate) {
    public static PetDTO of(Pet pet){
        return new PetDTO(
                pet.getName(),
                pet.getType(),
                pet.getGender(),
                pet.getBreed(),
                pet.getBirthDate()
        );
    }
    public static PetDTO of(PetView pet){
        return new PetDTO(
                pet.getName(),
                pet.getType(),
                pet.getGender(),
                pet.getBreed(),
                pet.getBirthDate()
        );
    }
}
