package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.tests.viewers.TestElement;

public class ChangeLabelAction extends TestSelectionAction {

	public ChangeLabelAction(String label, TestBrowser browser) {
		super(label, browser);
	}

	@Override
	public void run(TestElement element) {
		element.setLabel(element.getLabel() + " changed");
	}
}
