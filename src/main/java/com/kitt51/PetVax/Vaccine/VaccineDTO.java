package com.kitt51.PetVax.Vaccine;

import com.kitt51.PetVax.Vaccine.Command.Domain.Vaccine;
import com.kitt51.PetVax.Vaccine.Command.Domain.VaccineStatus;
import com.kitt51.PetVax.Vaccine.Query.Domain.VaccineView;

import java.time.LocalDate;
import java.util.Collection;

public record VaccineDTO(
        long petId,
        String name,
        String type,
        LocalDate date,
        VaccineStatus status)  {
    public static VaccineDTO of(Vaccine vaccine){
        return new VaccineDTO(
                vaccine.getPetId(),
                vaccine.getName(),
                vaccine.getType(),
                vaccine.getDate(),
                vaccine.getStatus());
    }
    public static VaccineDTO of(VaccineView vaccine){
        return new VaccineDTO(
                vaccine.getPetId(),
                vaccine.getName(),
                vaccine.getType(),
                vaccine.getDate(),
                vaccine.getStatus());
    }

}
