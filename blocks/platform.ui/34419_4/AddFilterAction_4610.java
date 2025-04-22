package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.tests.viewers.TestElement;
import org.eclipse.jface.tests.viewers.TestModelChange;

public class AddElementAction extends TestBrowserAction {

	public AddElementAction(String label, TestBrowser browser) {
		super(label, browser);
	}

	@Override
	public void run() {
		TestElement element = (TestElement) getBrowser().getViewer().getInput();
		element.addChild(TestModelChange.INSERT);
	}
}
