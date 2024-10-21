package com.example.springbatchquartz;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchJobConfig {

    @Bean
    public Job fileUploadToFTPJob(JobBuilderFactory jobBuilderFactory, Step uploadStep) {
        return jobBuilderFactory.get("fileUploadToFTPJob")
                .incrementer(new RunIdIncrementer())
                .start(uploadStep)
                .build();
    }

    @Bean
    public Job fileDownloadFromFTPJob(JobBuilderFactory jobBuilderFactory, Step downloadStep) {
        return jobBuilderFactory.get("fileDownloadFromFTPJob")
                .incrementer(new RunIdIncrementer())
                .start(downloadStep)
                .build();
    }

    @Bean
    public Step uploadStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("uploadStep")
                .tasklet((contribution, chunkContext) -> {
                    // Code for uploading file to FTP
                    System.out.println("Uploading file to FTP...");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step downloadStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("downloadStep")
                .tasklet((contribution, chunkContext) -> {
                    // Code for downloading file from FTP
                    System.out.println("Downloading file from FTP...");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}