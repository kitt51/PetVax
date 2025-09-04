package com.kitt51.PetVax.Exceptions;

public class VaccineQueryNextVaccineNotFound extends RuntimeException {
    public VaccineQueryNextVaccineNotFound(long id) {
        super(String.format("Vaccine with id=%d not found",id));
    }
    public static VaccineQueryNextVaccineNotFound of(long id){
        return new VaccineQueryNextVaccineNotFound(id);
    }
}
