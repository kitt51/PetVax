package com.kitt51.PetVax.Pet.Command.Domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
public record CreatePetRequest(

        @NotNull
        long ownerId,
        @NotEmpty
        String name,
        @NotEmpty
        String type,
        @NotEmpty
        String gender,
        @NotEmpty
        String breed,
        @PastOrPresent
        LocalDate birthDate) {
}
