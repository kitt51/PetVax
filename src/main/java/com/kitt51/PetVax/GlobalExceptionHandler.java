package com.kitt51.PetVax;

import com.kitt51.PetVax.Exceptions.PetCommandIdNotFoundException;
import com.kitt51.PetVax.Exceptions.PetViewIdNotFoundException;
import com.kitt51.PetVax.Exceptions.VaccineCommandIdNotFound;
import com.kitt51.PetVax.Exceptions.VaccineQueryLastVaccineNotFound;
import com.kitt51.PetVax.Exceptions.VaccineQueryNextVaccineNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({PetViewIdNotFoundException.class, PetCommandIdNotFoundException.class})
    public ResponseEntity<ApiError> handlePetIdNotFoundExceptions(RuntimeException e){
        ApiError error = new ApiError(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler({VaccineCommandIdNotFound.class,
            VaccineQueryLastVaccineNotFound.class, VaccineQueryNextVaccineNotFound.class})
    public ResponseEntity<ApiError> VaccineCommandIdNotFoundHandler(VaccineCommandIdNotFound e){
        ApiError error = new ApiError(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
