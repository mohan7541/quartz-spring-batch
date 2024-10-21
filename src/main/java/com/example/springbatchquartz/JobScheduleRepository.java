package com.example.springbatchquartz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobScheduleRepository extends JpaRepository<JobSchedule, Long> {
    List<JobSchedule> findAll();
}