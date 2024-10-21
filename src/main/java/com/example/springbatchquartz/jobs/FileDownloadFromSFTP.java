package com.example.springbatchquartz.jobs;

import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileDownloadFromSFTP implements Job {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job downloadJob; // Reference to your batch job for downloading


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRestartable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(JobExecution execution) {
	 try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(downloadJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public JobParametersIncrementer getJobParametersIncrementer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobParametersValidator getJobParametersValidator() {
		// TODO Auto-generated method stub
		return null;
	}
}
