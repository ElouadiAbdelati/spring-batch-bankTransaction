package com.example.springbatchdemo.bean;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankTransaction {
    @Id
    Long id;
    long accountID;
    Date transactionDate;
    @Transient
    String strTransactionDate;
    String transactionType;
    double amount;
}
