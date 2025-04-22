package org.eclipse.ui;

import org.eclipse.jface.action.IAction;

public interface IObjectActionDelegate extends IActionDelegate {
    public void setActivePart(IAction action, IWorkbenchPart targetPart);
}
