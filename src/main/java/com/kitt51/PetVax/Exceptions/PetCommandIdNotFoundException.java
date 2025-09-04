package com.kitt51.PetVax.Exceptions;

public class PetCommandIdNotFoundException extends RuntimeException {
    public PetCommandIdNotFoundException(long id) {
        super(String.format("Pet with id=%d not found",id));
    }
    public static PetCommandIdNotFoundException of(long id){
        return new PetCommandIdNotFoundException(id);
    }
}
