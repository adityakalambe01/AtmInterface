package com.atminterface.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atm_usage")
public class Atm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long atmId;
    private Long accountNumber;

    @Column(name = "atm_pin", columnDefinition = "int default 1234")
    private int atmPin;
}
