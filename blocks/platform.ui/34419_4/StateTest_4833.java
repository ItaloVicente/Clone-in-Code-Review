
package org.eclipse.ui.tests.commands;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class SimplyGoActionDelegate implements IWorkbenchWindowActionDelegate {
	static boolean executed = false;

	@Override
	public void dispose() {

	}

	@Override
	public void init(IWorkbenchWindow window) {

	}

	@Override
	public void run(IAction action) {
		executed = true;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {

	}

}
