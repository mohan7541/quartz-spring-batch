package com.example.springbatchquartz;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class QuartzConfig {

    @Autowired
    private JobScheduleRepository jobScheduleRepository;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @PostConstruct
    public void initializeJobs() throws SchedulerException {
    	Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<JobSchedule> jobSchedules = jobScheduleRepository.findAll();

        for (JobSchedule jobSchedule : jobSchedules) {
            if (jobSchedule.isActive()) {
                try {
                    Class<?> jobClass = Class.forName(jobSchedule.getJobClassName());
                    JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) jobClass)
                            .withIdentity(jobSchedule.getJobName(), "batch-jobs")
                            .build();

                    CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                            .withIdentity(jobSchedule.getJobName() + "Trigger", "batch-jobs")
                            .withSchedule(CronScheduleBuilder.cronSchedule(jobSchedule.getCronExpression()))
                            .build();

                    scheduler.scheduleJob(jobDetail, cronTrigger);
                } catch (ClassNotFoundException e) {
                    System.err.println("Job class not found: " + jobSchedule.getJobClassName());
                }
            }
        }

        scheduler.start();
    }
}