package org.eclipse.egit.ui.internal.decorators;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class GitDecoratorJob extends Job {

	private static final long DELAY = 10L;

	private static HashMap<String, GitDecoratorJob> jobs = new HashMap<String, GitDecoratorJob>();

	public static synchronized GitDecoratorJob getJobForRepository(
			final String gitDir) {
		GitDecoratorJob job = jobs.get(gitDir);
		if (job == null) {
			job = new GitDecoratorJob("GitDecoratorJob[" + gitDir + "]"); //$NON-NLS-1$ //$NON-NLS-2$
			job.setSystem(true);
			job.setPriority(DECORATE);
			jobs.put(gitDir, job);
		}
		return job;
	}

	private GitDecoratorJob(final String name) {
		super(name);
	}

	private HashSet<Object> elementList = new HashSet<Object>();

	public synchronized void addDecorationRequest(Object element) {
		if (elementList.add(element)) {
			if (getState() == NONE)
				schedule(DELAY);
		}
	}

	@Override
	public IStatus run(IProgressMonitor monitor) {
		while (!elementList.isEmpty()) {
			final Object[] elements;
			synchronized (this) {
				elements = elementList.toArray(new Object[elementList.size()]);
				elementList.clear();
			}
			GitLightweightDecorator.processDecoration(elements);
		}
		return Status.OK_STATUS;
	}
}
