package com.kitt51.PetVax.User.Repository;

import com.kitt51.PetVax.User.Model.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}
