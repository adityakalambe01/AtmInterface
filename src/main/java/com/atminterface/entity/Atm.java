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
public class ATM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long atmId;

    @OneToOne
    @JoinColumn(name = "accountNumber")
    private BankAccount accountNumber;

    private int atmPin;
}
