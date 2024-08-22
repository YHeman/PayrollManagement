package com.manthatech.PayrollManagement.service;
import com.manthatech.PayrollManagement.DTOS.JobDTO;
import com.manthatech.PayrollManagement.model.Job;
import com.manthatech.PayrollManagement.repository.JobRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Job createJob(JobDTO jobDTO) {
        Job job = new Job();
        job.setJobTitle(jobDTO.getJobTitle());
        job.setDescription(jobDTO.getDescription());
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());
        return jobRepository.save(job);
    }

    public List<JobDTO> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Job updateJob(Long jobId, JobDTO jobDTO) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();
            job.setJobTitle(jobDTO.getJobTitle());
            job.setDescription(jobDTO.getDescription());
            job.setUpdatedAt(LocalDateTime.now());
            return jobRepository.save(job);
        } else {
            throw new EntityNotFoundException("Job not found with id " + jobId);
        }
    }

    public void deleteJob(Long jobId) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isPresent()) {
            jobRepository.delete(optionalJob.get());
        } else {
            throw new EntityNotFoundException("Job not found with id " + jobId);
        }
    }

    private JobDTO convertToDTO(Job job) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setJobId(job.getJobId());
        jobDTO.setJobTitle(job.getJobTitle());
        jobDTO.setDescription(job.getDescription());
        return jobDTO;
    }
}
