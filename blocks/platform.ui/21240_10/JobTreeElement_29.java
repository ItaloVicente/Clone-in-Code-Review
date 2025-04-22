package org.eclipse.e4.ui.progress.internal;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Creatable;

@Creatable
@Singleton
public class JobInfoFactory {
	
	@Inject
	Services services;
	
	public JobInfo getJobInfo(Job enclosingJob) {
		return new JobInfo(enclosingJob,
		        services.getService(ProgressManager.class),
		        services.getService(FinishedJobs.class));
	}
}
