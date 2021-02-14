package com.example.loginregistrationlogout.appUser;


import org.hibernate.Session;

import java.util.Optional;

public interface AppUserRepository {

    Optional<AppUser> findByEmail(String email);
    long save(AppUser appUser);
    int enableAppUser(String email);
    void delete(AppUser appUser);
}
