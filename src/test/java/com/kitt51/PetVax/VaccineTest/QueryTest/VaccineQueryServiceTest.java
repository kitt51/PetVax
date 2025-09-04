package com.kitt51.PetVax.VaccineTest.QueryTest;

import com.kitt51.PetVax.Pet.PetDTO;
import com.kitt51.PetVax.Vaccine.Command.Domain.Vaccine;
import com.kitt51.PetVax.Vaccine.Command.Domain.VaccineStatus;
import com.kitt51.PetVax.Vaccine.Query.Domain.VaccineQueryRepository;
import com.kitt51.PetVax.Vaccine.Query.Domain.VaccineView;
import com.kitt51.PetVax.Vaccine.Query.Service.VaccineQueryService;
import com.kitt51.PetVax.Vaccine.VaccineDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class VaccineQueryServiceTest {

    @Mock
    VaccineQueryRepository queryRepository;

    @InjectMocks
    VaccineQueryService queryService;


    @Test
    void givenPetId_whenFindAllByPetId_thenReturnVaccineHistory(){
        List<VaccineView> vaccines = new ArrayList<VaccineView>();
        VaccineView vaccine1 = VaccineView.builder().
                petId(1L).
                name("test vaccine").
                type("type").
                dose(1f).
                date(LocalDate.now()).
                status(VaccineStatus.ADMINISTRATED).build();
        VaccineView vaccine2 = VaccineView.builder().
                petId(1L).
                name("test vaccine2").
                type("type2").
                dose(2.2f).
                date(LocalDate.now()).
                status(VaccineStatus.SCHEDULED).build();
        vaccines.add(vaccine1);
        vaccines.add(vaccine2);
        Mockito.when(queryRepository.findAllByPetId(Mockito.any())).thenReturn(vaccines);

        List<VaccineDTO> savedVaccines = queryService.findAllByPetId(1L);
        Mockito.verify(queryRepository).findAllByPetId(1L);

        Assertions.assertThat(savedVaccines.getFirst().name()).isEqualTo("test vaccine");
        Assertions.assertThat(savedVaccines.getFirst().type()).isEqualTo("type");
        Assertions.assertThat(savedVaccines.getFirst().status()).isEqualTo(VaccineStatus.ADMINISTRATED);

        Assertions.assertThat(savedVaccines.get(1).name()).isEqualTo("test vaccine2");
        Assertions.assertThat(savedVaccines.get(1).type()).isEqualTo("type2");
        Assertions.assertThat(savedVaccines.get(1).status()).isEqualTo(VaccineStatus.SCHEDULED);
    }

    @Test
    void givenPetID_whenFindNextScheduledVaccineByPetId_ThenReturnNextVaccine(){
        List<VaccineView> vaccines = new ArrayList<VaccineView>();
        VaccineView vaccine1 = VaccineView.builder().
                petId(1L).
                name("test vaccine").
                type("type").
                dose(1f).
                date(LocalDate.of(2026,9,9)).
                status(VaccineStatus.ADMINISTRATED).build();
        VaccineView vaccine2 = VaccineView.builder().
                petId(1L).
                name("test vaccine2").
                type("type2").
                dose(2.2f).
                date(LocalDate.of(2025,11,9)).
                status(VaccineStatus.SCHEDULED).build();
        vaccines.add(vaccine1);
        vaccines.add(vaccine2);
        Mockito.when(queryRepository.findNextVaccineByPetId(1L,LocalDate.now())).
                thenReturn(Optional.of(vaccine2));

        Optional<VaccineDTO> savedVaccine = queryService.findNextVaccineByPetId(1L);
        VaccineDTO nextVaccine = savedVaccine.get();

        Assertions.assertThat(nextVaccine.date()).isEqualTo(LocalDate.of(2025,11,9));
        Assertions.assertThat(nextVaccine.name()).isEqualTo("test vaccine2");


    }
}
