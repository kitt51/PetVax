package com.kitt51.PetVax.Vaccine.Command.Domain;

public record ChangeStatusVaccineCommand(
        long id,
        VaccineStatus status) {

    public static ChangeStatusVaccineCommand of(long id, ChangeStatusVaccineRequest request){
        return new ChangeStatusVaccineCommand(
                id,
                request.status()
        );
    }
}
