package com.kitt51.PetVax.Pet.Query.Service;



import com.kitt51.PetVax.Pet.Command.Domain.UpdatePetCommand;
import com.kitt51.PetVax.Pet.Command.Events.PetCreatedEvent;
import com.kitt51.PetVax.Pet.Command.Events.PetUpdatedEvent;
import com.kitt51.PetVax.Pet.Query.Domain.PetViewRepository;
import com.kitt51.PetVax.Pet.Query.Domain.PetView;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
public class PetEventHandler {
    private final PetViewRepository viewRepository;

    public PetEventHandler(PetViewRepository queryRepository) {
        this.viewRepository = queryRepository;
    }

    @ApplicationModuleListener
    public void on(PetCreatedEvent event){
        PetView view = PetView.builder()
                .petId(event.id())
                .ownerId(event.ownerId())
                .name(event.name())
                .type(event.type())
                .gender(event.gender())
                .breed(event.breed())
                .birthDate(event.birthDate()).build();

        viewRepository.save(view);
    }

    @ApplicationModuleListener
    public void on(PetUpdatedEvent event){
        PetView view = PetView.builder()
                .petId(event.id())
                .ownerId(event.ownerId())
                .name(event.name())
                .type(event.type())
                .gender(event.gender())
                .breed(event.breed())
                .birthDate(event.birthDate()).build();

        viewRepository.save(view);
    }
}
