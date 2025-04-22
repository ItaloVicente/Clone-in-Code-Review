package org.eclipse.ui.tests.api;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.tests.harness.util.CallHistory;

public class MockPartListener implements IPartListener {
    private CallHistory callTrace;

    public MockPartListener() {
        callTrace = new CallHistory(this);
    }

    public CallHistory getCallHistory() {
        return callTrace;
    }

    @Override
	public void partActivated(IWorkbenchPart part) {
        callTrace.add("partActivated");
    }

    @Override
	public void partBroughtToTop(IWorkbenchPart part) {
        callTrace.add("partBroughtToTop");
    }

    @Override
	public void partClosed(IWorkbenchPart part) {
        callTrace.add("partClosed");
    }

    @Override
	public void partDeactivated(IWorkbenchPart part) {
        callTrace.add("partDeactivated");
    }

    @Override
	public void partOpened(IWorkbenchPart part) {
        callTrace.add("partOpened");
    }
}
