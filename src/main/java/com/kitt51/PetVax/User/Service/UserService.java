package com.kitt51.PetVax.User.Service;

import com.kitt51.PetVax.User.Model.AppUser;
import com.kitt51.PetVax.User.Model.SecurityUser;
import com.kitt51.PetVax.User.Repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return  userRepository.findByEmail(email)
                .map(SecurityUser::new)
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
    }

    public void createUser(String email,String password, String role){
        AppUser user= new AppUser();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        userRepository.save(user);

    }

}
