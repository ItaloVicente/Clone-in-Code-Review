package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.tests.viewers.TestElement;

public class ChangeInputLabelAction extends TestBrowserAction {

    public ChangeInputLabelAction(String label, TestBrowser browser) {
        super(label, browser);
    }

	@Override
	public void run() {
        TestElement element = getBrowser().getInput();
        element.setLabel(element.getLabel() + " changed");
    }
}
