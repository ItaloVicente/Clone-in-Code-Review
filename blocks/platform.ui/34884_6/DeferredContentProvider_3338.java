package org.eclipse.ui.examples.jobs.actions;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class SuspendJobManagerAction implements IWorkbenchWindowActionDelegate {

	public void run(IAction action) {
		try {
			if (action.isChecked())
				Platform.getJobManager().suspend();
			else
				Platform.getJobManager().resume();
		} catch (OperationCanceledException e) {
			e.printStackTrace();
		}
	}
	public void selectionChanged(IAction action, ISelection selection) {
	}
	public void dispose() {
	}
	public void init(IWorkbenchWindow window) {
	}
}
