package com.example.springbatchdemo.dao;

import com.example.springbatchdemo.bean.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransactionDao extends JpaRepository<BankTransaction,Long> {
}
