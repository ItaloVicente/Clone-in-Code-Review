package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.viewers.StructuredSelection;

public class ClearSelectionAction extends TestBrowserAction {

	public ClearSelectionAction(String label, TestBrowser browser) {
		super(label, browser);
	}

	@Override
	public void run() {
		getBrowser().getViewer().setSelection(new StructuredSelection());
	}
}
