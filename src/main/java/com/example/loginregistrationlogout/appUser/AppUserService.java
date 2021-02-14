package com.example.loginregistrationlogout.appUser;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("user with email %s not found", email))
                );
    }

    public String signUpUser(AppUser appUser){
        boolean isExists = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if (isExists) {
            throw new IllegalStateException("user already exists");
        }

        String encodedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        // TODO: SEND confirmation token

        return "it works";
    }
}
