package com.example.loginregistrationlogout.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationTokenRepository {

    Optional<ConfirmationToken> findByToken(String token);
    long save(ConfirmationToken token);
    int updateConfirmedAt(ConfirmationToken token, LocalDateTime now);
    void delete(ConfirmationToken confirmationToken);

}
