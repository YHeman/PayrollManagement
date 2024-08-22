package com.manthatech.PayrollManagement.controller;

import com.manthatech.PayrollManagement.DTOS.JobDTO;
import com.manthatech.PayrollManagement.model.Job;
import com.manthatech.PayrollManagement.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody JobDTO jobDTO) {
        Job job = jobService.createJob(jobDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(job);
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<JobDTO> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<Job> updateJob(@PathVariable Long jobId, @RequestBody JobDTO jobDTO) {
        Job updatedJob = jobService.updateJob(jobId, jobDTO);
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteJob(@PathVariable Long jobId) {
        jobService.deleteJob(jobId);
        return ResponseEntity.ok().body("Job Deleted Successfully");
    }

}
