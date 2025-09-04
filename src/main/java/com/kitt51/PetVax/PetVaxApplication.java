package com.kitt51.PetVax;

import com.kitt51.PetVax.Pet.Query.Domain.PetView;
import com.kitt51.PetVax.Pet.Query.Domain.PetViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulith;
import org.springframework.modulith.Modulithic;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;


@SpringBootApplication
@EnableScheduling

public class PetVaxApplication {


	public static void main(String[] args) {
		SpringApplication.run(PetVaxApplication.class, args);
	}


}
