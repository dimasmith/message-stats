package net.anatolich.sunny.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.Date;
import java.time.Instant;

public class ImportJobLauncher {

    private final JobLauncher jobLauncher;
    private final Job importJob;

    @Autowired
    public ImportJobLauncher(JobLauncher jobLauncher, @Qualifier("importMessagesJob") Job importJob) {
        this.jobLauncher = jobLauncher;
        this.importJob = importJob;
    }

    /**
     * Import messages from XML file in specified URL.
     * @param url url of xml file with resources
     */
    public void importMessages(String url) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        final JobParameters jobParameters = new JobParametersBuilder()
                .addString("url", url)
                .addDate("invocationDate", Date.from(Instant.now()))
                .toJobParameters();
        jobLauncher.run(importJob, jobParameters);
    }
}
