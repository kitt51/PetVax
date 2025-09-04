package com.kitt51.PetVax.PetTest.WebTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitt51.PetVax.Pet.Command.Domain.Pet;
import com.kitt51.PetVax.Pet.Command.Service.PetCommandService;
import com.kitt51.PetVax.Pet.PetDTO;
import com.kitt51.PetVax.Pet.Query.Service.PetQueryService;
import com.kitt51.PetVax.Pet.Web.PetController;

import com.kitt51.PetVax.Security.UserSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
@WebMvcTest(PetController.class)
@EnableMethodSecurity
public class PetControllerTest {
    @MockitoBean
    private PetCommandService commandService;
    @MockitoBean
    private PetQueryService queryService;
    @MockitoBean(name = "userSecurity")
    private UserSecurity userSecurity;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(roles = "VET")
    void shouldAddValidPetTest_whenValidRole() throws Exception {
        Pet pet = Pet.builder()
                .id(1L)
                .ownerId(1L)
                .name("max")
                .type("DOG")
                .gender("MALE")
                .breed("Labrador")
                .birthDate(LocalDate.of(2020, 5, 10)).build();
        when(commandService.addPet(any())).thenReturn(PetDTO.of(pet));

        mockMvc.perform(post("/pet/v1")
                        .with(csrf())
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser(roles = "OWNER")
    void shouldNotAddPetTest_whenInvalidRole() throws Exception {
        Pet pet = Pet.builder()
                .id(1L)
                .ownerId(1L)
                .name("max")
                .type("DOG")
                .gender("MALE")
                .breed("Labrador")
                .birthDate(LocalDate.of(2020, 5, 10)).build();

        mockMvc.perform(post("/pet/v1")
                        .with(csrf())
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"VET"})
    void shouldFindPetByIdTest_whenValidRole() throws Exception {
        Pet pet = Pet.builder()
                .id(1L)
                .ownerId(1L)
                .name("max")
                .type("DOG")
                .gender("MALE")
                .breed("Labrador")
                .birthDate(LocalDate.of(2020, 5, 10)).build();
        when(queryService.findPetById(any())).thenReturn(Optional.of(PetDTO.of(pet)));
        mockMvc.perform(get("/pet/v1/{id}",1L)
                        .with(csrf())
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = {"VET"})
    void shouldNotFindPetByIdTest_whenValidRole() throws Exception {
        Pet pet = Pet.builder()
                .id(1L)
                .ownerId(1L)
                .name("max")
                .type("DOG")
                .gender("MALE")
                .breed("Labrador")
                .birthDate(LocalDate.of(2020, 5, 10)).build();
        when(queryService.findPetById(2L))
                .thenReturn(Optional.empty());
        mockMvc.perform(get("/pet/v1/{id}",2L)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser(roles = {"GUEST"})
    void shouldNotFindPetByIdTest_whenInvalidRole() throws Exception {
        Pet pet = Pet.builder()
                .id(1L)
                .ownerId(1L)
                .name("max")
                .type("DOG")
                .gender("MALE")
                .breed("Labrador")
                .birthDate(LocalDate.of(2020, 5, 10)).build();
        when(queryService.findPetById(any())).thenReturn(Optional.of(PetDTO.of(pet)));
        mockMvc.perform(get("/pet/v1/{id}",1L)
                        .with(csrf())
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"OWNER"})
    void shouldFindPetByIdTest_whenOwnerIdIsValid() throws Exception {
        when(userSecurity.hasAccessToPet(any(),any())).thenReturn(true);
        Pet pet = Pet.builder()
                .id(1L)
                .ownerId(1L)
                .name("max")
                .type("DOG")
                .gender("MALE")
                .breed("Labrador")
                .birthDate(LocalDate.of(2020, 5, 10)).build();
        when(queryService.findPetById(any())).thenReturn(Optional.of(PetDTO.of(pet)));
        mockMvc.perform(get("/pet/v1/{id}",1L)
                        .with(csrf())
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = {"OWNER"})
    void shouldNotFindPetByIdTest_whenOwnerIdIsNotEqualsPetOwnerId() throws Exception {
        when(userSecurity.hasAccessToPet(any(),any())).thenReturn(false);
        Pet pet = Pet.builder()
                .id(1L)
                .ownerId(1L)
                .name("max")
                .type("DOG")
                .gender("MALE")
                .breed("Labrador")
                .birthDate(LocalDate.of(2020, 5, 10)).build();
        when(queryService.findPetById(any())).thenReturn(Optional.of(PetDTO.of(pet)));
        mockMvc.perform(get("/pet/v1/{id}",1L)
                        .with(csrf())
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isForbidden());
    }




}
