package com.kitt51.PetVax.Vaccine.Command.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data @AllArgsConstructor @Builder
@Table(name = "VACCINE")
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name = "id")
    private long id;
    @Column(nullable = false,name = "pet_id")
    private long petId;
    @Column(nullable = false,name = "name")
    private String name; //  vaccine name
    @Column(nullable = false,name = "type")
    private String type; // vaccine type
    @Column(nullable = false,name = "dose")
    private float dose;
    @Column(nullable = false,name = "vaccine_date")
    private LocalDate date;
    @Column(nullable = false,name = "status")
    private VaccineStatus status;


}
