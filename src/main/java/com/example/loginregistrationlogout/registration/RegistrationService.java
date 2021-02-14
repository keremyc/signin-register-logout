package com.example.loginregistrationlogout.registration;

import com.example.loginregistrationlogout.appUser.AppUser;
import com.example.loginregistrationlogout.appUser.AppUserRole;
import com.example.loginregistrationlogout.appUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final AppUserService appUserService;

    public String register(RegistrationRequest request) {
        boolean isEmailValid = emailValidator.test(request.getEmail());
        if (!isEmailValid)
            throw new IllegalStateException("email not valid");

        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );

    }
}
