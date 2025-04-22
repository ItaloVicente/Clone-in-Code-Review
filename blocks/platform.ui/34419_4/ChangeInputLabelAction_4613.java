package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.tests.viewers.TestElement;

public class ChangeChildLabelAction extends TestSelectionAction {
	public ChangeChildLabelAction(String label, TestBrowser browser) {
		super(label, browser);
	}

	@Override
	public void run(TestElement element) {
		TestElement child = element.getFirstChild();
		if (child != null) {
			child.setLabel(child.getLabel() + " renamed child");
		}
	}
}
