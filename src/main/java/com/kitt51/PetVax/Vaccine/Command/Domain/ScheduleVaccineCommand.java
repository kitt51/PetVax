package com.kitt51.PetVax.Vaccine.Command.Domain;

import java.time.LocalDate;

public record ScheduleVaccineCommand(
        long petId,
        String name,
        String type,
        LocalDate date,
        float dose) {
    public static ScheduleVaccineCommand of(ScheduleVaccineRequest request){
        return new ScheduleVaccineCommand(
                request.petId(),
                request.name(),
                request.type(),
                request.date(),
                request.dose()
        );
    }
}
