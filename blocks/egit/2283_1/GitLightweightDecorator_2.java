package org.eclipse.egit.ui.internal.decorators;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class GitDecoratorJob extends Job {

	private static final int DELAY = 100;

	private static HashMap<String, GitDecoratorJob> jobs = new HashMap<String, GitDecoratorJob>();

	public static synchronized GitDecoratorJob getJobForRepository(
			final String gitDir) {
		GitDecoratorJob job = jobs.get(gitDir);
		if (job == null) {
			System.out
					.println("create new GitDecoratorJob for gitDir=" + gitDir); //$NON-NLS-1$
			job = new GitDecoratorJob("GitDecoratorJob[" + gitDir + "]"); //$NON-NLS-1$ //$NON-NLS-2$
			job.setSystem(true); // TODO ???
			job.setPriority(DECORATE);
			job.schedule(DELAY);
			jobs.put(gitDir, job);
		}
		return job;
	}

	private ArrayList<Object> elementList = new ArrayList<Object>();

	private GitDecoratorJob(final String name) {
		super(name);
	}

	@Override
	public IStatus run(IProgressMonitor monitor) {

		while (!elementList.isEmpty()) {
			final Object[] elements;
			synchronized (this) {
				elements = elementList.toArray(new Object[elementList.size()]);
				elementList.clear();
			}

			System.out
					.println("GitDecoratorJob: performing " + elements.length + " decoration(s)"); //$NON-NLS-1$ //$NON-NLS-2$
			GitLightweightDecorator.processDecoration(elements);
		}

		return Status.OK_STATUS;
	}

	public synchronized void addDecorationRequest(Object element) {
		if (!elementList.contains(element)) {
			elementList.add(element);

			if (getState() == NONE)
				schedule(DELAY);
			if (getState() == SLEEPING)
				wakeUp(DELAY);
		}
	}
}
