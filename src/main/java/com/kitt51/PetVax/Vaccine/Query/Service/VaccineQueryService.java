package com.kitt51.PetVax.Vaccine.Query.Service;

import com.kitt51.PetVax.Vaccine.Query.Domain.VaccineQueryRepository;
import com.kitt51.PetVax.Exceptions.VaccineQueryLastVaccineNotFound;
import com.kitt51.PetVax.Exceptions.VaccineQueryNextVaccineNotFound;
import com.kitt51.PetVax.Vaccine.VaccineDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VaccineQueryService {

    private final VaccineQueryRepository queryRepository;

    public VaccineQueryService(VaccineQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }
    public List<VaccineDTO> findAllByPetId(Long id){
        return queryRepository.findAllByPetId(id)
                .stream().map(VaccineDTO::of)
                .collect(Collectors.toList());
    }

    public Optional<VaccineDTO> findNextVaccineByPetId(Long id){
        return Optional.ofNullable(queryRepository.findNextVaccineByPetId(id, LocalDate.now())
                .map(VaccineDTO::of)
                .orElseThrow(() -> VaccineQueryNextVaccineNotFound.of(id)));

    }
    public Optional<VaccineDTO> findLastVaccineByPetId(Long id){
        return Optional.ofNullable(queryRepository.findFirstByPetIdAndDateLessThanEqualOrderByDateDesc(id, LocalDate.now())
                .map(VaccineDTO::of)
                .orElseThrow(() -> VaccineQueryLastVaccineNotFound.of(id)));
    }
    
}
