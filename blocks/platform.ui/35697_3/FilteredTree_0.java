package org.eclipse.e4.ui.dialogs.filteredtree;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

public abstract class BasicUIJob extends Job {

	private Display cachedDisplay;

	public BasicUIJob(String name, Display display) {
		super(name);
		this.cachedDisplay = display;
	}

	@Override
	public final IStatus run(final IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			return Status.CANCEL_STATUS;
		}
		Display asyncDisplay = (cachedDisplay == null) ? getDisplay()
				: cachedDisplay;
		if (asyncDisplay == null || asyncDisplay.isDisposed()) {
			return Status.CANCEL_STATUS;
		}
		asyncDisplay.asyncExec(new Runnable() {
			@Override
			public void run() {
				IStatus result = null;
				try {
					setThread(Thread.currentThread());
					if (monitor.isCanceled()) {
						result = Status.CANCEL_STATUS;
					} else {
						result = runInUIThread(monitor);
					}
				} finally {
					done(result);
				}
			}
		});
		return Job.ASYNC_FINISH;
	}

	public abstract IStatus runInUIThread(IProgressMonitor monitor);

	public Display getDisplay() {
		return (cachedDisplay != null) ? cachedDisplay : Display.getCurrent();
	}
}
