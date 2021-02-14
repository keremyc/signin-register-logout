package com.example.loginregistrationlogout.appUser;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AppUserRepositoryImpl implements AppUserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<AppUser> findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        AppUser appUser = session.createQuery(
                "from AppUser u where u.email = :email", AppUser.class)
                .setParameter("email", email)
                .getSingleResult();

        return Optional.of(appUser);
    }

}
