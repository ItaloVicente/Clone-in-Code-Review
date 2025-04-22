package org.eclipse.ui.tests.api;

import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.tests.harness.util.CallHistory;

public class MockPropertyListener implements IPropertyListener {
    private CallHistory callTrace;

    private Object sourceMask;

    private int sourceId;

    public MockPropertyListener(Object source, int id) {
        sourceMask = source;
        sourceId = id;
        callTrace = new CallHistory(this);
    }

    @Override
	public void propertyChanged(Object source, int propId) {
        if (source == sourceMask && propId == sourceId) {
			callTrace.add("propertyChanged");
		}
    }

    public CallHistory getCallHistory() {
        return callTrace;
    }
}

