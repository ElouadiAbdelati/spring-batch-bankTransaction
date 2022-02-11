package com.example.springbatchdemo.config;

import com.example.springbatchdemo.bean.BankTransaction;
import com.example.springbatchdemo.dao.BankTransactionDao;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.text.SimpleDateFormat;
import java.util.List;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {


    @Bean
    public FlatFileItemReader<BankTransaction> flatFileItemReader(@Value("${inputFile}") Resource inputFile) {
        FlatFileItemReader<BankTransaction> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setName("CSV-TRADER");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setResource(inputFile);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<BankTransaction> lineMapper() {
        DefaultLineMapper<BankTransaction> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "accountID", "strTransactionDate", "transactionType", "amount");
        lineMapper.setLineTokenizer(lineTokenizer);
        BeanWrapperFieldSetMapper<BankTransaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(BankTransaction.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public ItemProcessor<BankTransaction, BankTransaction> getItemProcessor(){
        return new ItemProcessor<BankTransaction, BankTransaction>() {
            private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
            @Override
            public BankTransaction process(BankTransaction bankTransaction) throws Exception {
                bankTransaction.setTransactionDate(dateFormat.parse(bankTransaction.getStrTransactionDate()));
                return bankTransaction;
            }
        };
    }

    @Bean
    public ItemWriter<BankTransaction> getItemWriter() {
        return new ItemWriter<BankTransaction>() {
            @Autowired
            private BankTransactionDao bankTransactionDao;
            @Override
            public void write(List<? extends BankTransaction> items) throws Exception {
                bankTransactionDao.saveAll(items);
            }
        };
    }


}
