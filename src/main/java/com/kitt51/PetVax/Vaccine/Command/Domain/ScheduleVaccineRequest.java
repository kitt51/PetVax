package com.kitt51.PetVax.Vaccine.Command.Domain;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record ScheduleVaccineRequest(
        @NotNull
        long petId,
        @NotNull
        String name,
        @NotNull
        String type,
        @FutureOrPresent
        LocalDate date,
        @Positive
        float dose) {
}
