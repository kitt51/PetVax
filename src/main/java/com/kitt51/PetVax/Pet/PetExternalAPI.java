package com.kitt51.PetVax.Pet;

import com.kitt51.PetVax.Pet.Command.Domain.CreatePetCommand;
import com.kitt51.PetVax.Pet.Command.Domain.Pet;
import com.kitt51.PetVax.Pet.Command.Domain.UpdatePetCommand;

public interface PetExternalAPI {
    public PetDTO addPet(CreatePetCommand cmd);
    public PetDTO updatePet(UpdatePetCommand cmd);
}
