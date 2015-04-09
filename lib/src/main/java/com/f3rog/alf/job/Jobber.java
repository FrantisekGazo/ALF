package com.f3rog.alf.job;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.JobManager;

import javax.inject.Inject;

/**
 * Class {@link Jobber} provides JobManager for whole application
 *
 * @author f3rog
 * @version 2015-03-07
 */
public class Jobber {

    @Inject
    static JobManager s_jobManager;

    public static void addJob(Job job) {
        s_jobManager.addJob(job);
    }
}
