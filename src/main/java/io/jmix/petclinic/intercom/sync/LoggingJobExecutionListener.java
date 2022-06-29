package io.jmix.petclinic.intercom.sync;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LoggingJobExecutionListener extends JobListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(LoggingJobExecutionListener.class);

    @Autowired
    private Scheduler scheduler;

    @Override
    public String getName() {
        return "LoggingJobExecutionListener";
    }

    @PostConstruct
    private void registerListener() {
        try {
            scheduler.getListenerManager().addJobListener(this);
        } catch (SchedulerException e) {
            log.error("Cannot register job listener", e);
        }
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info("jobToBeExecuted: name={}, context={}",
            context.getJobDetail().getKey().getName(), context);
        super.jobToBeExecuted(context);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context,
                               JobExecutionException jobException) {
        log.info("jobWasExecuted: name={}, context={}",
                context.getJobDetail().getKey().getName(), context);
    }
}