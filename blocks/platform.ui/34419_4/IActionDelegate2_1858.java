package org.eclipse.ui;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;

public interface IActionDelegate {
    public void run(IAction action);

    public void selectionChanged(IAction action, ISelection selection);
}
