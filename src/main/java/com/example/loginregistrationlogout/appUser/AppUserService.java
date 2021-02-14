package com.example.loginregistrationlogout.appUser;

import com.example.loginregistrationlogout.registration.token.ConfirmationToken;
import com.example.loginregistrationlogout.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
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

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableAppUser(String email){
        return appUserRepository.enableAppUser(email);
    }

    public void deleteUser(AppUser appUser) {
        appUserRepository.delete(appUser);
    }
}
