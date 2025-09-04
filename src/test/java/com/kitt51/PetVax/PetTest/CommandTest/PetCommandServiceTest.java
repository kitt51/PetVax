package com.kitt51.PetVax.PetTest.CommandTest;



import com.kitt51.PetVax.Pet.Command.Domain.CreatePetCommand;
import com.kitt51.PetVax.Pet.Command.Domain.Pet;
import com.kitt51.PetVax.Pet.Command.Domain.PetCommandRepository;
import com.kitt51.PetVax.Pet.Command.Domain.UpdatePetCommand;
import com.kitt51.PetVax.Pet.Command.Events.PetCreatedEvent;
import com.kitt51.PetVax.Pet.Command.Events.PetUpdatedEvent;
import com.kitt51.PetVax.Pet.Command.Service.PetCommandService;
import com.kitt51.PetVax.Pet.PetDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PetCommandServiceTest {

    @Mock
    PetCommandRepository commandRepository;
    @Mock
    ApplicationEventPublisher eventPublisher;

    @InjectMocks
    PetCommandService commandService;


    @Test
    void givenValidPet_whenCreatePet_thenPetIsSaved(){
        //arrange
        CreatePetCommand cmd = new CreatePetCommand(
                1L, "max", "DOG", "MALE", "Labrador", LocalDate.of(2020, 5, 10)
        );
        Pet pet = Pet.builder()
                .ownerId(cmd.ownerId())
                .name(cmd.name())
                .type(cmd.type())
                .gender(cmd.gender())
                .breed(cmd.breed())
                .birthDate(cmd.birthDate()).build();

        Mockito.when(commandRepository.save(pet)).thenReturn(pet);

        // act
        PetDTO petResult = commandService.addPet(cmd);
        Mockito.verify(commandRepository).save(Mockito.any(Pet.class));
        Mockito.verify(eventPublisher).publishEvent(Mockito.any(PetCreatedEvent.class));

        //assert
        Assertions.assertThat(petResult.name()).isEqualTo(pet.getName());
        Assertions.assertThat(petResult.type()).isEqualTo(pet.getType());
        Assertions.assertThat(petResult.gender()).isEqualTo(pet.getGender());



    }

    @Test
    void givenValidInfo_whenUpdatePet_thenUpdateChanges(){
        //ARRANGE
        UpdatePetCommand cmd = new UpdatePetCommand(
                1L, "bolt", "DOG", "MALE", "Labrador", LocalDate.of(2020, 5, 10)
        );
        Pet pet = Pet.builder()
                .id(1L)
                .ownerId(1L)
                .name("max")
                .type("DOG")
                .gender("MALE")
                .breed("Labrador")
                .birthDate(LocalDate.of(2020, 5, 10)).build();

        Mockito.when(commandRepository.save(Mockito.any(Pet.class))).thenReturn(pet);
        Mockito.when(commandRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(pet));
        //act
        PetDTO petResult = commandService.updatePet(cmd);
        Mockito.verify(commandRepository).save(Mockito.any(Pet.class));
        Mockito.verify(eventPublisher).publishEvent(Mockito.any(PetUpdatedEvent.class));

        //assert
        Assertions.assertThat(petResult.name()).isEqualTo("bolt");
        Assertions.assertThat(petResult.type()).isEqualTo("DOG");
        Assertions.assertThat(petResult.gender()).isEqualTo("MALE");


    }




}
