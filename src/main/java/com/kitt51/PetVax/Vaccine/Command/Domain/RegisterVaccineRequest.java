package com.kitt51.PetVax.Vaccine.Command.Domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegisterVaccineRequest(
        @NotNull
        long petId,
        @NotNull
        String name,
        @NotNull
        String type,
        @Positive
        float dose) {
}
