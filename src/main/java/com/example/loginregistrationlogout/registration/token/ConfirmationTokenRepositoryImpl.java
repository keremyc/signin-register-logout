package com.example.loginregistrationlogout.registration.token;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
@Transactional
public class ConfirmationTokenRepositoryImpl implements ConfirmationTokenRepository {

    public final SessionFactory sessionFactory;

    @Autowired
    public ConfirmationTokenRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<ConfirmationToken> findByToken(String token) {
        Session session = sessionFactory.getCurrentSession();

        ConfirmationToken tokenFound;
        try {
            tokenFound = session
                            .createQuery("from ConfirmationToken where token = :token", ConfirmationToken.class)
                            .getSingleResult();
            return Optional.of(tokenFound);
        } catch (NoResultException exp){
            return Optional.empty();
        }

    }

    @Override
    public long save(ConfirmationToken token) {
        Session session = sessionFactory.getCurrentSession();
        return (long) session.save(token);
    }
}
