package io.jmix.petclinic.intercom.sync;

import io.jmix.core.security.Authenticated;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class IntercomOwnerSyncJob implements Job {

    @Autowired
    IntercomOwnerSync intercomOwnerSync;

    @Override
    @Authenticated
    public void execute(JobExecutionContext context) {
        log.info("Intercom Owner Sync Job started");

        final boolean appsSyncedSuccessfully = intercomOwnerSync.sync();

        if (appsSyncedSuccessfully) {
            log.info("Intercom Owner Sync Job successfully finished");
        }
        else {
            log.error("Intercom Owner Sync Job did not finished successfully.");
        }
    }
}