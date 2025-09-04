package com.kitt51.PetVax.PetTest.QueryTest;


import com.kitt51.PetVax.Pet.PetDTO;
import com.kitt51.PetVax.Pet.Query.Domain.PetView;
import com.kitt51.PetVax.Pet.Query.Domain.PetViewRepository;
import com.kitt51.PetVax.Pet.Query.Service.PetQueryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PetQueryServiceTest {

    @Mock
    PetViewRepository viewRepository;
    @InjectMocks
    PetQueryService queryService;

    @Test
    void givenValidPetId_whenFindPetById_thenReturnPetInfo(){
        long petId = 1L;
        PetView pet = PetView.builder()
                .petId(1L)
                .ownerId(1L)
                .name("bolt")
                .type("DOG")
                .gender("MALE")
                .breed("Labrador")
                .birthDate(LocalDate.of(2020, 5, 10)).build();

        Mockito.when(viewRepository.findById(petId)).thenReturn(Optional.ofNullable(pet));

        Optional<PetDTO> savedPet = queryService.findPetById(petId);

        Assertions.assertThat(savedPet.get().name()).isEqualTo("bolt");
        Assertions.assertThat(savedPet.get().type()).isEqualTo("DOG");
        Assertions.assertThat(savedPet.get().breed()).isEqualTo("Labrador");
        Assertions.assertThat(savedPet.get().gender()).isEqualTo("MALE");
        Assertions.assertThat(savedPet.get().birthDate()).isEqualTo(LocalDate.of(2020, 5, 10));

    }

}
