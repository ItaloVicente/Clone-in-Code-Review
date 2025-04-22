package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;

public class SetSorterAction extends TestBrowserAction {

    public SetSorterAction(String label, TestBrowser browser) {
        super(label, browser);
    }

    @Override
	public void run() {
        Viewer viewer = getBrowser().getViewer();
        if (viewer instanceof StructuredViewer) {
            StructuredViewer v = (StructuredViewer) viewer;
            v.setSorter(new Sorter());
        }
    }
}
