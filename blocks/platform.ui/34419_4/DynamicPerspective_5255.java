package org.eclipse.ui.dynamic;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class MockViewActionDelegate implements IViewActionDelegate {

	public MockViewActionDelegate() {
		super();
	}

	public void init(IViewPart view) {
	}

	public void run(IAction action) {

	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
