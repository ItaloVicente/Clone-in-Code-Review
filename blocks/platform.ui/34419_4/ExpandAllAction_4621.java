package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.tests.viewers.TestElement;

public class DeleteSiblingsAction extends TestSelectionAction {

	boolean fAll = false;

	public DeleteSiblingsAction(String label, TestBrowser browser, boolean all) {
		super(label, browser);
		fAll = all;
	}

	@Override
	public void run(TestElement element) {
		if (fAll) {
			element.getContainer().deleteChildren();
		} else {
			element.getContainer().deleteSomeChildren();
		}
	}
}
