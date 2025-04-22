
package org.eclipse.ui.tests.progress;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

public class DummyJob extends Job {

	private final IStatus status;

	public DummyJob(String name, IStatus status) {
		super(name);
		this.status = status;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {

		monitor.beginTask(getName() + " starts now", 10);
		try {
			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
				monitor.worked(1);
				if (monitor.isCanceled()) {
					break;
				}
			}
			return status;
		} finally {
			monitor.done();
		}
	}

}
