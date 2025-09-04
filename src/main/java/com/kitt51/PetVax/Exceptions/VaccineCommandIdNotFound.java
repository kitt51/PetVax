package com.kitt51.PetVax.Exceptions;

public class VaccineCommandIdNotFound extends RuntimeException {
    public VaccineCommandIdNotFound(long id) {
        super(String.format("Vaccine with id=%d not found",id));
    }
    public static VaccineCommandIdNotFound of(long id){
        return new VaccineCommandIdNotFound(id);
    }
}
