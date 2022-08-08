package io.jmix.petclinic.intercom.sync;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
public class IntercomOwnerSyncJobConfiguration {

    @Bean
    public JobDetail intercomOwnerSyncJobDetail() {
        return JobBuilder.newJob().ofType(IntercomOwnerSyncJob.class)
            .storeDurably()
            .withIdentity(IntercomOwnerSyncJob.class.getSimpleName())
            .withDescription("Sync Owner to Intercom")
            .build();
    }

    @Bean
    public Trigger intercomOwnerSyncTrigger(JobDetail intercomOwnerSyncJobDetail) {
        return TriggerBuilder.newTrigger().forJob(intercomOwnerSyncJobDetail)
            .withIdentity(intercomOwnerSyncJobDetail.getKey() + "Trigger")
            .withDescription("Every minute")
            .withSchedule(simpleSchedule().repeatForever().withIntervalInMinutes(2))
            .build();
    }

}