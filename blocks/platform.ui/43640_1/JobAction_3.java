package org.eclipse.ui.examples.jobs.actions;

import org.eclipse.core.internal.jobs.JobManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class DebugJobManagerAction implements IWorkbenchWindowActionDelegate {
	public DebugJobManagerAction() {
		super();
	}
	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}

	public void run(IAction action) {
		System.out.println("**** BEGIN DUMP JOB MANAGER INFORMATION ****"); //$NON-NLS-1$
		Job[] jobs = Platform.getJobManager().find(null);
		for (int i = 0; i < jobs.length; i++) {
			System.out.println("" + jobs[i].getClass().getName() + " state: " + JobManager.printState(jobs[i].getState())); //$NON-NLS-1$ //$NON-NLS-2$
		}
		System.out.println("**** END DUMP JOB MANAGER INFORMATION ****"); //$NON-NLS-1$
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
}
