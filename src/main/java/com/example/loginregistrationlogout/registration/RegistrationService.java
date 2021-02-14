package com.example.loginregistrationlogout.registration;

import com.example.loginregistrationlogout.appUser.AppUser;
import com.example.loginregistrationlogout.appUser.AppUserRole;
import com.example.loginregistrationlogout.appUser.AppUserService;
import com.example.loginregistrationlogout.registration.token.ConfirmationToken;
import com.example.loginregistrationlogout.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;

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

    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(
                        () -> new IllegalStateException("token not found")
                );

        if (confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())){
            confirmationTokenService.deleteToken(confirmationToken);
            appUserService.deleteUser(confirmationToken.getAppUser());
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(confirmationToken);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
}
