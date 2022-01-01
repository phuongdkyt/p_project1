package com.example.demo.models.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long billId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "bill_name")
    private String billName;
    @Column(name = "bill_detail")
    private String billDetail;

}
