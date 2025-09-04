package com.kitt51.PetVax.Vaccine.Command.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data @AllArgsConstructor @Builder
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long petId;
    private String name; //  vaccine name
    private String type; // vaccine type
    private float dose;
    private LocalDate date;
    private VaccineStatus status;


}
