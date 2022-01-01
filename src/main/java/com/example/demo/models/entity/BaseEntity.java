package com.example.demo.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;


@MappedSuperclass
@Getter
@Setter
public class BaseEntity extends EntityUtil{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(columnDefinition = "datetime default now()")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @PrePersist()
    @Override
    public void onCreate() {
        createdAt = ZonedDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    @Override
    protected void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }
}
