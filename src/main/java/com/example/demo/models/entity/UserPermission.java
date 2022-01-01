package com.example.demo.models.entity;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "user_pers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "permission_id")
    private Long perId;
    //    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private ZonedDateTime license;

}
