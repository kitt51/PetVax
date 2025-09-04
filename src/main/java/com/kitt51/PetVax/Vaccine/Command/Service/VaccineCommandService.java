package com.kitt51.PetVax.Vaccine.Command.Service;


import com.kitt51.PetVax.Vaccine.Command.Domain.*;
import com.kitt51.PetVax.Vaccine.Command.Events.VaccineRegisteredEvent;
import com.kitt51.PetVax.Vaccine.Command.Events.VaccineScheduledEvent;
import com.kitt51.PetVax.Vaccine.Command.Events.VaccineUpdatedEvent;
import com.kitt51.PetVax.Exceptions.VaccineCommandIdNotFound;
import com.kitt51.PetVax.Vaccine.VaccineDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class VaccineCommandService {
    private final VaccineCommandRepository commandRepository;
    private final ApplicationEventPublisher eventPublisher;

    public VaccineCommandService(VaccineCommandRepository commandRepository, ApplicationEventPublisher eventPublisher) {
        this.commandRepository = commandRepository;
        this.eventPublisher = eventPublisher;
    }
    public VaccineDTO registerVaccineAdministrated(RegisterVaccineCommand cmd){
        Vaccine vaccine = Vaccine.builder().
                petId(cmd.petId()).
                name(cmd.name()).
                type(cmd.type()).
                dose(cmd.dose()).
                date(LocalDate.now()).
                status(VaccineStatus.ADMINISTRATED).build();
        Vaccine savedVaccine = commandRepository.save(vaccine);

        eventPublisher.publishEvent(
                new VaccineRegisteredEvent(
                    savedVaccine.getId(),savedVaccine.getPetId(), savedVaccine.getName(),
                    savedVaccine.getType(),savedVaccine.getDose(), savedVaccine.getDate(),
                    savedVaccine.getStatus()));

        return VaccineDTO.of(savedVaccine);
    }

    public VaccineDTO scheduleVaccine(ScheduleVaccineCommand cmd){
        Vaccine vaccine = Vaccine.builder().
                petId(cmd.petId()).
                name(cmd.name()).
                type(cmd.type()).
                dose(cmd.dose()).
                date(cmd.date()).
                status(VaccineStatus.SCHEDULED).build();
        Vaccine savedVaccine = commandRepository.save(vaccine);

        eventPublisher.publishEvent(
                new VaccineScheduledEvent(
                        savedVaccine.getId(),savedVaccine.getPetId(), savedVaccine.getName(),
                        savedVaccine.getType(),savedVaccine.getDose(), savedVaccine.getDate(),
                        savedVaccine.getStatus()));

        return VaccineDTO.of(savedVaccine);
    }

    public VaccineDTO changeStatusScheduledVaccine(ChangeStatusVaccineCommand cmd){
        Optional<Vaccine> existingVaccine = commandRepository.findById(cmd.id());
        if(existingVaccine.isEmpty()){
            throw new VaccineCommandIdNotFound(cmd.id());
        }
        Vaccine updatedVaccine = existingVaccine.get();
        updatedVaccine.setStatus(cmd.status());
        Vaccine savedVaccine = commandRepository.save(updatedVaccine);

        eventPublisher.publishEvent(
                new VaccineUpdatedEvent(
                        updatedVaccine.getId(),updatedVaccine.getPetId(), updatedVaccine.getName(),
                        updatedVaccine.getType(),updatedVaccine.getDose(), updatedVaccine.getDate(),
                        updatedVaccine.getStatus()));

        return VaccineDTO.of(savedVaccine);
    }

}
