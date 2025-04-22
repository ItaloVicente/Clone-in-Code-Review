package org.eclipse.egit.ui.test;

import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

public class JobJoiner {

	private final Object jobFamily;
	private final long timeoutMillis;

	private Job scheduledJob = null;
	private boolean done = false;

	private final IJobChangeListener listener = new JobChangeAdapter() {
		@Override
		public void scheduled(IJobChangeEvent event) {
			if (event.getJob().belongsTo(jobFamily))
				scheduledJob = event.getJob();
		}

		@Override
		public void done(IJobChangeEvent event) {
			if (event.getJob() != null && event.getJob() == scheduledJob) {
				done = true;
				Job.getJobManager().removeJobChangeListener(this);
			}
		}
	};

	public static JobJoiner startListening(Object jobFamily, long timeoutDuration,
			TimeUnit timeoutUnit) {
		return new JobJoiner(jobFamily, timeoutUnit.toMillis(timeoutDuration));
	}

	private JobJoiner(Object jobFamily, long timeoutMillis) {
		this.jobFamily = jobFamily;
		this.timeoutMillis = timeoutMillis;
		Job.getJobManager().addJobChangeListener(listener);
	}

	public void join() {
		try {
			doJoin();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Thread was interrupted.", e);
		} finally {
			Job.getJobManager().removeJobChangeListener(listener);
		}
	}

	private void doJoin() throws AssertionError, InterruptedException {
		long timeSlept = 0;
		while (!done) {
			if (timeSlept > timeoutMillis) {
				if (scheduledJob == null)
					throw new AssertionError(
							"Job was not scheduled until timeout of "
									+ timeoutMillis + " ms.");
				else if (!done)
					throw new AssertionError(
							"Job was scheduled but not done until timeout of "
									+ timeoutMillis + " ms.");
			}
			Thread.sleep(100);
			timeSlept += 100;
		}
	}
}
