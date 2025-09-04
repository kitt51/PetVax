package com.kitt51.PetVax.Vaccine.Command.Domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChangeStatusVaccineRequest(

        @NotNull
        VaccineStatus status) {
}
