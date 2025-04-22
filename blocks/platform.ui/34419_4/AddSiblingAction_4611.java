package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;

public class AddFilterAction extends TestBrowserAction {

	public AddFilterAction(String label, TestBrowser browser) {
		super(label, browser);
	}

	@Override
	public void run() {
		Viewer viewer = getBrowser().getViewer();
		if (viewer instanceof StructuredViewer) {
			((StructuredViewer) viewer).addFilter(new Filter());
		}
	}
}
