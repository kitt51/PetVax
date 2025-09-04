package com.kitt51.PetVax.Vaccine.Web;


import com.kitt51.PetVax.Pet.PetDTO;
import com.kitt51.PetVax.Vaccine.Command.Domain.*;
import com.kitt51.PetVax.Vaccine.Command.Service.VaccineCommandService;
import com.kitt51.PetVax.Vaccine.Query.Service.VaccineQueryService;
import com.kitt51.PetVax.Vaccine.VaccineDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.event.ListDataEvent;
import java.util.List;

@RestController
@RequestMapping("/vaccine/v1")
public class VaccineController {
    private final VaccineCommandService commandService;
    private final VaccineQueryService queryService;

    public VaccineController(VaccineCommandService commandService, VaccineQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_VET')")
    public ResponseEntity<VaccineDTO> registerVaccine(
            @Validated @RequestBody RegisterVaccineRequest request){
        RegisterVaccineCommand cmd = RegisterVaccineCommand.of(request);
        VaccineDTO vaccine =commandService.registerVaccineAdministrated(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(vaccine);
    }
    @PostMapping("/schedule")
    @PreAuthorize("hasRole('ROLE_VET')")
    public ResponseEntity<VaccineDTO> scheduleVaccine(
            @Validated @RequestBody ScheduleVaccineRequest request){
        ScheduleVaccineCommand cmd = ScheduleVaccineCommand.of(request);
        VaccineDTO vaccine =commandService.scheduleVaccine(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(vaccine);
    }
    @PutMapping("/schedule")
    @PreAuthorize("hasRole('ROLE_VET')")
    public  ResponseEntity<VaccineDTO> changeStatusScheduledVaccine(
            @PathVariable long id,
            @Validated @RequestBody ChangeStatusVaccineRequest request){
        ChangeStatusVaccineCommand cmd = ChangeStatusVaccineCommand.of(id,request);
        VaccineDTO vaccine = commandService.changeStatusScheduledVaccine(cmd);
        return ResponseEntity.ok(vaccine);
    }
    @GetMapping("/{id}/history")
    @PreAuthorize("hasRole('ROLE_VET') or   @userSecurity.hasAccessToPet(authentication, #id)")
    public ResponseEntity<List<VaccineDTO>> findVaccineHistoryByPetId(
            @PathVariable(name = "id") long id) {
         return ResponseEntity.ok(queryService.findAllByPetId(id));

    }

    @GetMapping("/{id}/history/filter")
    @PreAuthorize("hasRole('ROLE_VET') or  @userSecurity.hasAccessToPet(authentication, #id)")
    public ResponseEntity<VaccineDTO> findVaccineHistoryByPetIdFilter(
            @PathVariable(name = "id") long id,
            @RequestParam(name = "filterBy") String filterBy) {

        return switch (filterBy.toLowerCase()) {
            case "nextvaccine" -> queryService.findNextVaccineByPetId(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
            case "lastvaccine" -> queryService.findLastVaccineByPetId(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
            default -> ResponseEntity.badRequest().build();
        };
    }


}
