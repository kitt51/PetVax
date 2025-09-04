package com.kitt51.PetVax.Vaccine.Command.Events;

import com.kitt51.PetVax.Vaccine.Command.Domain.VaccineStatus;

import java.time.LocalDate;

public record VaccineUpdatedEvent(
        long id,
        long petId,
        String name,
        String type,
        float dose,
        LocalDate date,
        VaccineStatus status) {
}
