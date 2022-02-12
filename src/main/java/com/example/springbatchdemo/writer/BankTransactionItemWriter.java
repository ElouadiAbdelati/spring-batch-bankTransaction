package com.example.springbatchdemo.writer;

import com.example.springbatchdemo.bean.BankTransaction;
import com.example.springbatchdemo.dao.BankTransactionDao;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BankTransactionItemWriter implements ItemWriter<BankTransaction> {
    @Autowired
    private BankTransactionDao bankTransactionDao;

    @Override
    public void write(List<? extends BankTransaction> list) throws Exception {
        bankTransactionDao.saveAll(list);
    }
}
