package org.eclipse.ui.dynamic;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class MockObjectActionDelegate implements IObjectActionDelegate, IActionDelegate {
	
	public MockObjectActionDelegate() {
		super();
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void run(IAction action) {
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
}
