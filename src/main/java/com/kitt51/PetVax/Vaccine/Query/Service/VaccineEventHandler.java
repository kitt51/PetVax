package com.kitt51.PetVax.Vaccine.Query.Service;


import com.kitt51.PetVax.Vaccine.Command.Events.VaccineRegisteredEvent;
import com.kitt51.PetVax.Vaccine.Command.Events.VaccineScheduledEvent;
import com.kitt51.PetVax.Vaccine.Command.Events.VaccineUpdatedEvent;
import com.kitt51.PetVax.Exceptions.VaccineCommandIdNotFound;
import com.kitt51.PetVax.Vaccine.Query.Domain.VaccineQueryRepository;
import com.kitt51.PetVax.Vaccine.Query.Domain.VaccineView;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VaccineEventHandler {
    private final VaccineQueryRepository queryRepository;

    public VaccineEventHandler(VaccineQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }
    @ApplicationModuleListener
    public void on(VaccineRegisteredEvent event){
        VaccineView vaccine = new VaccineView(event.id(),
                event.petId(),event.name(),event.type(),event.dose(),event.date(),event.status());
        queryRepository.save(vaccine);
    }
    @ApplicationModuleListener
    public void on(VaccineScheduledEvent event){
        VaccineView vaccine = new VaccineView(event.id(),
                event.petId(),event.name(),event.type(),event.dose(),event.date(),event.status());
        queryRepository.save(vaccine);
    }
    @ApplicationModuleListener
    public void on(VaccineUpdatedEvent event){
        Optional<VaccineView> existingVaccine = queryRepository.findById(Long.valueOf(event.id()));
        if(existingVaccine.isEmpty()){
            throw new VaccineCommandIdNotFound(event.id());
        }
        VaccineView vaccine = new VaccineView(event.id(),
                event.petId(),event.name(),event.type(),event.dose(),event.date(),event.status());
        queryRepository.save(vaccine);
    }

}
