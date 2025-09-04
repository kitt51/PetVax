package com.kitt51.PetVax.Pet.Query.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Builder @AllArgsConstructor @Data @NoArgsConstructor
@Document("PetView")
public class PetView {
        @Id
        private long petId;
        private long ownerId;
        private String name;
        private String type;
        String gender;
        String breed;
        LocalDate birthDate;

}
