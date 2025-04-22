package org.eclipse.ui;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Event;

@Deprecated
public interface IActionDelegateWithEvent {

    @Deprecated
	public void runWithEvent(IAction action, Event event);

}
