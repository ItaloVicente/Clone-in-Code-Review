package org.eclipse.ui.tests.api;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.tests.harness.util.CallHistory;

public class MockActionDelegate implements IWorkbenchWindowActionDelegate {

    public CallHistory callHistory;

    public static final String ACTION_SET_ID = "org.eclipse.ui.tests.api.MockActionSet";

    public static MockActionDelegate lastDelegate;

    public MockActionDelegate() {
        callHistory = new CallHistory(this);
        lastDelegate = this;
    }

    @Override
	public void init(IWorkbenchWindow window) {
    }

    @Override
	public void run(IAction action) {
        callHistory.add("run");
    }

    @Override
	public void selectionChanged(IAction action, ISelection selection) {
        callHistory.add("selectionChanged");
    }

    @Override
	public void dispose() {
    }

}

