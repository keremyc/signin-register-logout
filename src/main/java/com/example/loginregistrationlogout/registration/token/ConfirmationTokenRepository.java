package com.example.loginregistrationlogout.registration.token;

import java.util.Optional;

public interface ConfirmationTokenRepository {

    Optional<ConfirmationToken> findByToken(String token);
    long save(ConfirmationToken token);

}
