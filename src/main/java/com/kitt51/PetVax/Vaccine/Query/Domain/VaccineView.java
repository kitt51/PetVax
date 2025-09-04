package com.kitt51.PetVax.Vaccine.Query.Domain;

import com.kitt51.PetVax.Vaccine.Command.Domain.VaccineStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
@Document("VaccineView")
public class VaccineView {
        @Id
        private long id;
        private long petId;
        private String name;
        private String type;
        private float dose;
        private LocalDate date;
        private VaccineStatus status;
}
