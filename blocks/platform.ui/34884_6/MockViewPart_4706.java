package org.eclipse.ui.tests.api;

import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class MockViewActionDelegate extends MockActionDelegate implements
        IViewActionDelegate {
    public MockViewActionDelegate() {
        super();
    }

    @Override
	public void init(IViewPart view) {
        callHistory.add("init");
    }
}

