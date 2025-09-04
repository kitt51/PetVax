package com.kitt51.PetVax.Pet.Command.Events;

import java.time.LocalDate;

public record PetUpdatedEvent(
        long id,
        long ownerId,
        String name,
        String type,
        String gender,
        String breed,
        LocalDate birthDate) {
}
