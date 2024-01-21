package com.atminterface.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "back_account_details")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    private String accountHolderName;

    private Long mobileNumber;

    @Column(name = "account_holder_balance", columnDefinition = "double default 0")
    private Double accountHolderBalance;

}
