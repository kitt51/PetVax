package com.kitt51.PetVax.VaccineTest.QueryTest;


import com.kitt51.PetVax.Vaccine.Command.Domain.VaccineStatus;
import com.kitt51.PetVax.Vaccine.Query.Domain.VaccineQueryRepository;
import com.kitt51.PetVax.Vaccine.Query.Domain.VaccineView;
import com.kitt51.PetVax.Vaccine.VaccineDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

@DataMongoTest
public class VaccineQueryRepositoryTest {

    @Autowired
    private VaccineQueryRepository queryRepository;

    @AfterEach
    void cleanUp() {
        queryRepository.deleteAll();
    }

    @Test
    void testFindNextScheduledVaccine(){
        VaccineView vaccine1 = VaccineView.builder().
                id(1L).
                petId(1L).
                name("test vaccine").
                type("type").
                dose(1f).
                date(LocalDate.of(2026,9,9)).
                status(VaccineStatus.SCHEDULED).build();
        VaccineView vaccine2 = VaccineView.builder().
                id(2L).
                petId(1L).
                name("test vaccine2").
                type("type2").
                dose(2.2f).
                date(LocalDate.of(2025,11,9)).
                status(VaccineStatus.SCHEDULED).build();

        queryRepository.save(vaccine1);
        queryRepository.save(vaccine2);
        Optional<VaccineView> savedVaccine = queryRepository.findNextVaccineByPetId(1L,LocalDate.now());
        VaccineView nextVaccine = savedVaccine.get();
        Assertions.assertThat(nextVaccine.getDate()).isEqualTo(LocalDate.of(2025,11,9));
    }

    @Test
    void testFindLastVaccine(){
        VaccineView vaccine1 = VaccineView.builder().
                id(1L).
                petId(1L).
                name("test vaccine").
                type("type").
                dose(1f).
                date(LocalDate.of(2025,1,8)).
                status(VaccineStatus.ADMINISTRATED).build();
        VaccineView vaccine2 = VaccineView.builder().
                id(2L).
                petId(1L).
                name("test vaccine2").
                type("type2").
                dose(2.2f).
                date(LocalDate.of(2024,11,9)).
                status(VaccineStatus.SCHEDULED).build();

        queryRepository.save(vaccine1);
        queryRepository.save(vaccine2);

        Optional<VaccineView> savedVaccine = queryRepository.findFirstByPetIdAndDateLessThanEqualOrderByDateDesc(1L,LocalDate.now());
        VaccineView nextVaccine = savedVaccine.get();
        Assertions.assertThat(nextVaccine.getDate()).isEqualTo(LocalDate.of(2025,1,8));
    }

}
