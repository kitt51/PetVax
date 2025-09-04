package com.kitt51.PetVax.Exceptions;

public class VaccineQueryLastVaccineNotFound extends RuntimeException {
    public VaccineQueryLastVaccineNotFound(long id) {
        super(String.format("Vaccine with id=%d not found",id));
    }
    public static VaccineQueryLastVaccineNotFound of(long id){
        return new VaccineQueryLastVaccineNotFound(id);
    }
}
