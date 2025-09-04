package com.kitt51.PetVax.Exceptions;

public class PetViewIdNotFoundException extends RuntimeException {
    public PetViewIdNotFoundException(long id) {
        super(String.format("Pet with id=%d not found",id));
    }
    public static PetCommandIdNotFoundException of(long id){
        return new PetCommandIdNotFoundException(id);
    }
}
