package com.kitt51.PetVax.Pet.Command.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "PET")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,name = "owner_id")
    private long ownerId;
    @Column(nullable = false,name = "name")
    private String name;
    @Column(nullable = false,name = "type")
    private String type;
    @Column(nullable = false,name = "gender")
    private String gender;
    @Column(nullable = false,name = "breed")
    private String breed;
    @Column(nullable = false,name = "birthdate")
    private LocalDate birthDate;

}
