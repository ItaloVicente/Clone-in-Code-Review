package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.Viewer;

public class ExpandAllAction extends TestBrowserAction {

	public ExpandAllAction(String label, TestBrowser browser) {
		super(label, browser);
	}

	@Override
	public void run() {
		Viewer viewer = getBrowser().getViewer();
		if (viewer instanceof AbstractTreeViewer) {
			((AbstractTreeViewer) viewer).expandAll();
		}
	}
}
