package org.eclipse.ui.examples.jobs.actions;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class JobAction implements IWorkbenchWindowActionDelegate {
	public void run(IAction action) {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		Job job = new WorkspaceJob("Background job") { //$NON-NLS-1$
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				monitor.beginTask("Doing something in background", 100); //$NON-NLS-1$
				for (int i = 0; i < 100; i++) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						return Status.CANCEL_STATUS;
					}
					monitor.worked(1);
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(workspace.getRoot());
		job.schedule();
	}
	public void selectionChanged(IAction action, ISelection selection) {
	}
	public void dispose() {
	}
	public void init(IWorkbenchWindow window) {
	}
}
