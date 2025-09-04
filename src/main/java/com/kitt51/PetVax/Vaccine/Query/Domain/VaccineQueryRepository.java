package com.kitt51.PetVax.Vaccine.Query.Domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VaccineQueryRepository extends MongoRepository<VaccineView, Long> {

    List<VaccineView> findAllByPetId(Long petId);
    @Query(
            value = "{ 'petId': ?0, 'date': { $gte: ?1 }, 'status': 'SCHEDULED' }",
            sort = "{ 'date': 1 }"
    )
    Optional<VaccineView> findNextVaccineByPetId(Long id, LocalDate today);

    Optional<VaccineView> findFirstByPetIdAndDateLessThanEqualOrderByDateDesc(Long petId, LocalDate today);


}
