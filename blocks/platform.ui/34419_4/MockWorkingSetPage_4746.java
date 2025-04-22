package org.eclipse.ui.tests.api;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class MockWorkbenchWindowActionDelegate extends MockActionDelegate
        implements IWorkbenchWindowActionDelegate, IActionDelegate2 {
    public static MockWorkbenchWindowActionDelegate lastDelegate;

    public static String SET_ID = "org.eclipse.ui.tests.api.MockActionSet";

    public static String ID = "org.eclipse.ui.tests.api.MockWindowAction";

    public MockWorkbenchWindowActionDelegate() {
        super();
        lastDelegate = this;
    }

    @Override
	public void init(IWorkbenchWindow window) {
        callHistory.add("init");
    }

    @Override
	public void dispose() {
        callHistory.add("dispose");
    }

	@Override
	public void init(IAction action) {
		callHistory.add("init");
	}

	@Override
	public void runWithEvent(IAction action, Event event) {
		callHistory.add("runWithEvent");
	}
}

