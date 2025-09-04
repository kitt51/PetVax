package com.kitt51.PetVax.VaccineTest.WebTest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitt51.PetVax.Security.UserSecurity;
import com.kitt51.PetVax.Vaccine.Command.Domain.Vaccine;
import com.kitt51.PetVax.Vaccine.Command.Domain.VaccineStatus;
import com.kitt51.PetVax.Vaccine.Command.Service.VaccineCommandService;
import com.kitt51.PetVax.Vaccine.Query.Domain.VaccineView;
import com.kitt51.PetVax.Vaccine.Query.Service.VaccineQueryService;
import com.kitt51.PetVax.Vaccine.VaccineDTO;
import com.kitt51.PetVax.Vaccine.Web.VaccineController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@WebMvcTest(VaccineController.class)
@EnableMethodSecurity
public class VaccineControllerTest {
    @MockitoBean
    private VaccineCommandService commandService;
    @MockitoBean
    private VaccineQueryService queryService;
    @MockitoBean(name = "userSecurity")
    private UserSecurity userSecurity;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "VET")
    void shouldRegisterVaccine_whenRoleIsValid() throws Exception {
        Vaccine vaccine = Vaccine.builder()
                .id(1L)
                .petId(1L)
                .name("vaccine")
                .type("test")
                .dose(1f)
                .build();
        Mockito.when(commandService.registerVaccineAdministrated(Mockito.any()))
                .thenReturn(VaccineDTO.of(vaccine));

        mockMvc.perform(post("/vaccine/v1/register")
                        .with(csrf())
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(vaccine)))
                .andExpect(status().isCreated());

    }
    @Test
    @WithMockUser(roles = "OWNER")
    void shouldNotRegisterVaccine_whenRoleIsInvalid() throws Exception {
        Vaccine vaccine = Vaccine.builder()
                .id(1L)
                .petId(1L)
                .name("vaccine")
                .type("test")
                .dose(1f)
                .build();
        Mockito.when(commandService.registerVaccineAdministrated(Mockito.any()))
                .thenReturn(VaccineDTO.of(vaccine));

        mockMvc.perform(post("/vaccine/v1/register")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(vaccine)))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(roles = "VET")
    void shouldScheduleVaccine_whenRoleIsValid() throws Exception {
        Vaccine vaccine = Vaccine.builder()
                .id(1L)
                .petId(1L)
                .name("vaccine")
                .type("test")
                .dose(1f)
                .date(LocalDate.of(2026,9,12))
                .build();
        Mockito.when(commandService.scheduleVaccine(Mockito.any()))
                .thenReturn(VaccineDTO.of(vaccine));

        mockMvc.perform(post("/vaccine/v1/register")
                        .with(csrf())
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(vaccine)))
                .andExpect(status().isCreated());
    }
    @Test
    @WithMockUser(roles = {"VET"})
    void shouldFindNextScheduledVaccine() throws Exception {
            VaccineView vaccine = VaccineView.builder().
                    id(1L).
                    petId(1L).
                    name("test vaccine").
                    type("type").
                    dose(1f).
                    date(LocalDate.of(2026,9,9)).
                    status(VaccineStatus.ADMINISTRATED).build();
            Mockito.when(queryService.findNextVaccineByPetId(Mockito.any()))
                    .thenReturn(Optional.of(VaccineDTO.of(vaccine)));

            mockMvc.perform(get("/vaccine/v1/{id}/history/filter?filterBy={filter}",1L,"nextvaccine")
                            .with(csrf())
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                            .content(objectMapper.writeValueAsString(VaccineDTO.of(vaccine))))
                    .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = {"OWNER"})
    void shouldFindHistoryByPetId_whenOwnerIdIsValid() throws Exception {
        when(userSecurity.hasAccessToPet(any(),any())).thenReturn(true);
        when(queryService.findAllByPetId(Mockito.any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/vaccine/v1/{id}/history",1L)
                        .with(csrf())
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk());

    }
    @Test
    @WithMockUser(roles = {"OWNER"})
    void shouldNotFindHistoryByPetId_whenOwnerIdIsInvalid() throws Exception {
        when(userSecurity.hasAccessToPet(any(),any())).thenReturn(false);
        when(queryService.findAllByPetId(Mockito.any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/vaccine/v1/{id}/history",1L)
                        .with(csrf())
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isForbidden());

    }
}

