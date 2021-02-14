package com.example.loginregistrationlogout.registration.token;

import com.example.loginregistrationlogout.appUser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @SequenceGenerator(
            name = "confirmation_sequence",
            sequenceName = "confirmation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    private LocalDateTime confirmedAt;

    public ConfirmationToken(String token, LocalDateTime createdAt,
                             LocalDateTime expiredAt, AppUser appUser) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.appUser = appUser;
    }

    @ManyToOne
    @JoinColumn(nullable = false, name = "app_user_id")
    private AppUser appUser;

}
