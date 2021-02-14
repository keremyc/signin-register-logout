package com.example.loginregistrationlogout.appUser;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
@Transactional
public class AppUserRepositoryImpl implements AppUserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<AppUser> findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();

        AppUser appUser;
        try {
            appUser = session.createQuery(
                    "from AppUser u where u.email = :email", AppUser.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(appUser);
        } catch (NoResultException exp){
            return Optional.empty();
        }

    }

    @Override
    public long save(AppUser appUser) {
        Session session = sessionFactory.getCurrentSession();
        return (long) session.save(appUser);
    }

}
