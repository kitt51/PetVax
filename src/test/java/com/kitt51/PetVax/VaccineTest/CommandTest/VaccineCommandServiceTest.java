package com.kitt51.PetVax.VaccineTest.CommandTest;

import com.kitt51.PetVax.Vaccine.Command.Domain.*;
import com.kitt51.PetVax.Vaccine.Command.Events.VaccineRegisteredEvent;
import com.kitt51.PetVax.Vaccine.Command.Events.VaccineScheduledEvent;
import com.kitt51.PetVax.Vaccine.Command.Service.VaccineCommandService;
import com.kitt51.PetVax.Vaccine.VaccineDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class VaccineCommandServiceTest {

    @Mock
    private VaccineCommandRepository commandRepository;
    @Mock
    ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private VaccineCommandService commandService;

    @Captor
    ArgumentCaptor<Vaccine> vaccineCaptor;

    @Test
    void givenVaccineCMD_whenRegisterVaccine_thenPetVaccineIsPersisted(){
        //arrange
        RegisterVaccineCommand cmd = new RegisterVaccineCommand(1L,"Vaccine","type",5.5f);
        Vaccine vaccine = Vaccine.builder().
                petId(cmd.petId()).
                name(cmd.name()).
                type(cmd.type()).
                dose(cmd.dose()).
                date(LocalDate.now()).
                status(VaccineStatus.ADMINISTRATED).build();
        Mockito.when(commandRepository.save(Mockito.any(Vaccine.class))).thenReturn(vaccine);
        //act
        VaccineDTO registeredVaccine = commandService.registerVaccineAdministrated(cmd);
        Mockito.verify(commandRepository).save(vaccine);
        Mockito.verify(eventPublisher).publishEvent(Mockito.any(VaccineRegisteredEvent.class));

        //assert
        Assertions.assertThat(registeredVaccine.petId()).isEqualTo(1L);
        Assertions.assertThat(registeredVaccine.name()).isEqualTo("Vaccine");
        Assertions.assertThat(registeredVaccine.type()).isEqualTo("type");

    }

    @Test
    void givenScheduleCMD_whenScheduleVaccine_thenScheduledVaccineIsPersisted(){

        LocalDate scheduledDate = LocalDate.now();
        ScheduleVaccineCommand cmd = new ScheduleVaccineCommand(1L,"Vaccine","type",scheduledDate,5.5f);

        Vaccine vaccine = Vaccine.builder().
                petId(cmd.petId()).
                name(cmd.name()).
                type(cmd.type()).
                dose(cmd.dose()).
                date(LocalDate.now()).
                status(VaccineStatus.SCHEDULED).build();
        Mockito.when(commandRepository.save(Mockito.any(Vaccine.class))).thenReturn(vaccine);

        VaccineDTO scheduledVaccine = commandService.scheduleVaccine(cmd);
        Mockito.verify(commandRepository).save(vaccine);
        Mockito.verify(eventPublisher).publishEvent(Mockito.any(VaccineScheduledEvent.class));

        Assertions.assertThat(scheduledVaccine.petId()).isEqualTo(1L);
        Assertions.assertThat(scheduledVaccine.name()).isEqualTo("Vaccine");
        Assertions.assertThat(scheduledVaccine.date()).isEqualTo(scheduledDate);


    }

    @Test
    void givenAdministratedCMD_whenChangeStatusScheduledVaccine_thenReturnPetVaccineHistory(){
        ChangeStatusVaccineCommand cmd = new ChangeStatusVaccineCommand(1L,VaccineStatus.ADMINISTRATED);
        Vaccine vaccine = Vaccine.builder()
                .id(1L)
                .status(VaccineStatus.SCHEDULED).build();
        Mockito.when(commandRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(vaccine));

       commandService.changeStatusScheduledVaccine(cmd);

       Mockito.verify(commandRepository).save(vaccineCaptor.capture());
       Vaccine savedVaccine = vaccineCaptor.getValue();

       Assertions.assertThat(savedVaccine.getStatus()).isEqualTo(VaccineStatus.ADMINISTRATED);

    }

}
