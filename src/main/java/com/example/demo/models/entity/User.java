package com.example.demo.models.entity;


import com.example.demo.models.dto.AuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "users",indexes = {
        @Index(name = "idx_username", columnList = "username"),
})
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "status", columnDefinition = "varchar(15) default 'ACTIVE'")
    private String status;

    @CreationTimestamp
    private ZonedDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "lastest_login_date")
    private ZonedDateTime lastLoginDate;

    @PrePersist()
    private void onCreate() {
        createdDate = ZonedDateTime.now();
    }

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "image")
    private String image;

    @Column(name = "email")
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(name = "provider_id")
    private String providerId;

}
