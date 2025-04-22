package org.eclipse.jface.tests.viewers.interactive;

public class FlushInputAction extends TestBrowserAction {

	public FlushInputAction(String label, TestBrowser browser) {
		super(label, browser);
	}

	@Override
	public void run() {
		getBrowser().setInput(null);
	}
}
