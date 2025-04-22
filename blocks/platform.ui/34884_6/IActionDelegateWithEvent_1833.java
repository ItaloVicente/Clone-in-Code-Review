package org.eclipse.ui;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Event;

public interface IActionDelegate2 extends IActionDelegate {
    public void init(IAction action);

    public void dispose();

    public void runWithEvent(IAction action, Event event);
}
