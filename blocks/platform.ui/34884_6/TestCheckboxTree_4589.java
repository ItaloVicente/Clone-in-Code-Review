package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.action.Action;

public abstract class TestBrowserAction extends Action {
    private TestBrowser browser;

    public TestBrowserAction(String label, TestBrowser browser) {
        super(label);
        this.browser = browser;
    }

    public TestBrowser getBrowser() {
        return browser;
    }
}
