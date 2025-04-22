package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.viewers.StructuredViewer;

public class WorldChangedAction extends TestBrowserAction {

    public WorldChangedAction(String label, TestBrowser browser) {
        super(label, browser);
    }

    @Override
	public void run() {
        ((StructuredViewer) getBrowser().getViewer()).refresh();
    }
}
