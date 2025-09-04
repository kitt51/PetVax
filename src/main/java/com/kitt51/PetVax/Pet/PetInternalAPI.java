package com.kitt51.PetVax.Pet;


import java.util.Optional;

public interface PetInternalAPI {
    public Optional<PetDTO> findPetById(Long id);
}
