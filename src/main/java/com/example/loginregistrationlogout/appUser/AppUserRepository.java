package com.example.loginregistrationlogout.appUser;


import java.util.Optional;

public interface AppUserRepository {

    Optional<AppUser> findByEmail(String email);
    long save(AppUser appUser);

}
