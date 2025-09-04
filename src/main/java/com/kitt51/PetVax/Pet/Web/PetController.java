package com.kitt51.PetVax.Pet.Web;


import com.kitt51.PetVax.Pet.Command.Domain.*;
import com.kitt51.PetVax.Pet.Command.Service.PetCommandService;
import com.kitt51.PetVax.Pet.PetDTO;
import com.kitt51.PetVax.Pet.PetExternalAPI;
import com.kitt51.PetVax.Pet.PetInternalAPI;
import com.kitt51.PetVax.Pet.Query.Service.PetQueryService;
import com.kitt51.PetVax.User.Model.SecurityUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/pet/v1")
public class PetController  {

    private final PetCommandService commandService;
    private final PetQueryService queryService;

    public PetController(PetCommandService commandService, PetQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_VET')")
    public ResponseEntity<PetDTO> addPet(@Valid @RequestBody CreatePetRequest request ){

        CreatePetCommand cmd = CreatePetCommand.of(request);
        PetDTO pet = commandService.addPet(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(pet);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_VET') or  @userSecurity.hasAccessToPet(authentication, #id)")
    public ResponseEntity<PetDTO> updatePet(
            @PathVariable long id,
            @Valid @RequestBody UpdatePetRequest request){

        UpdatePetCommand cmd = UpdatePetCommand.of(id,request);
        PetDTO pet = commandService.updatePet(cmd);
        return ResponseEntity.ok(pet);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_VET') or  @userSecurity.hasAccessToPet(authentication, #id)")
    public ResponseEntity<PetDTO> findPetById( @PathVariable(name = "id") long id){
       return queryService.findPetById(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(()->ResponseEntity.notFound().build());


    }




}
