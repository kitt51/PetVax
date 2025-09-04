package com.kitt51.PetVax.Vaccine.Command.Domain;

import java.time.LocalDate;

public record VaccineEmailDTO(
        String petName,
        String email,
        String name,
        LocalDate date) {
}
