package com.kitt51.PetVax.Vaccine.Command.Domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineCommandRepository extends JpaRepository<Vaccine,Long> {
    @Query("""
            SELECT new com.kitt51.PetVax.Vaccine.Command.Domain.VaccineEmailDTO
            (p.name,u.email,v.name,v.date)
            FROM Vaccine v
            LEFT JOIN Pet p
            ON v.petId = p.id
            LEFT JOIN AppUser u
            ON u.id = p.ownerId
            WHERE v.date = :date
            """)
    List<VaccineEmailDTO> getEmailData(@Param("date") LocalDate date);

}
