package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.tests.viewers.TestElement;

public class DeleteChildrenAction extends TestSelectionAction {

	boolean fAll = false;

	public DeleteChildrenAction(String label, TestBrowser browser, boolean all) {
		super(label, browser);
		fAll = all;
	}

	@Override
	public void run(TestElement element) {
		if (fAll) {
			element.deleteChildren();
		} else {
			element.deleteSomeChildren();
		}
	}
}
