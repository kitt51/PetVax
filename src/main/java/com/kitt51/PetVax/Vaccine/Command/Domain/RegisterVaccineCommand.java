package com.kitt51.PetVax.Vaccine.Command.Domain;

import java.time.LocalDate;

public record RegisterVaccineCommand(
        long petId,
        String name,
        String type,
        float dose) {
    public static RegisterVaccineCommand of(RegisterVaccineRequest request){
        return new RegisterVaccineCommand(
                request.petId(),
                request.name(),
                request.type(),
                request.dose()
        );
    }
}
