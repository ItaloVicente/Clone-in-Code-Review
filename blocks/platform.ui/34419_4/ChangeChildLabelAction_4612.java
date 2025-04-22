package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.tests.viewers.TestElement;
import org.eclipse.jface.tests.viewers.TestModelChange;

public class AddSiblingAction extends TestSelectionAction {

	int fEventKind;

	public AddSiblingAction(String label, TestBrowser browser) {
		this(label, browser, TestModelChange.INSERT);
	}

	public AddSiblingAction(String label, TestBrowser browser, int eventKind) {
		super(label, browser);
		fEventKind = eventKind;
	}

	@Override
	public void run(TestElement element) {
		element.getContainer().addChild(fEventKind);
	}
}
