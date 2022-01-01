package com.example.demo.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Locale;

@Getter
@Setter
@Entity
public class WeddingHall extends BaseEntity{
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "image")
    private String image;

    private String status;

//    private String locale;
}
