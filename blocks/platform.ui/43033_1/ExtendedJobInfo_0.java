package org.eclipse.ui.tests.progress;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.internal.progress.JobInfo;

class ExtendedJobInfo extends JobInfo {

	public ExtendedJobInfo(Job enclosingJob) {
		super(enclosingJob);
	}

	@Override
	public String toString() {
		return "ExtendedJobInfo [getName()=" + getJob().getName() + ", getPriority()=" + getJob().getPriority()
				+ ", getState()=" + getJob().getState() + ", isSystem()=" + getJob().isSystem() + ", isUser()="
				+ getJob().isUser() + "]";
	}

}
