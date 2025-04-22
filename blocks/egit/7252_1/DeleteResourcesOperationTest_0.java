package org.eclipse.egit.core.test;

import static org.junit.Assert.assertTrue;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

public class JobSchedulingAssert extends JobChangeAdapter {

	private final Object jobFamily;

	private boolean scheduled = false;

	public static JobSchedulingAssert forFamily(Object jobFamily) {
		return new JobSchedulingAssert(jobFamily);
	}

	private JobSchedulingAssert(Object jobFamily) {
		this.jobFamily = jobFamily;
		Job.getJobManager().addJobChangeListener(this);
	}

	@Override
	public void scheduled(IJobChangeEvent event) {
		if (event.getJob().belongsTo(jobFamily))
			scheduled = true;
	}

	public void assertScheduled(String messageForFailure) {
		Job.getJobManager().removeJobChangeListener(this);
		assertTrue(messageForFailure, scheduled);
	}
}
