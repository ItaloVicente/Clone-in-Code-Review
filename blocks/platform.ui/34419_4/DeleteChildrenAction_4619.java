package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.tests.viewers.TestElement;

public class DeleteAction extends TestSelectionAction {

	public DeleteAction(String label, TestBrowser browser) {
		super(label, browser);
	}

	@Override
	public void run(TestElement element) {
		element.getContainer().deleteChild(element);
	}
}
