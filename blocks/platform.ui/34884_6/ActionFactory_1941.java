package org.eclipse.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IActionDelegate2;

public abstract class ActionDelegate implements IActionDelegate2 {
    @Override
	public void run(IAction action) {
    }

    @Override
	public void selectionChanged(IAction action, ISelection selection) {
    }

    @Override
	public void init(IAction action) {
    }

    @Override
	public void dispose() {
    }

    @Override
	public void runWithEvent(IAction action, Event event) {
        run(action);
    }
}
