package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.tests.viewers.TestElement;
import org.eclipse.jface.tests.viewers.TestModelContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

public class TestList extends TestBrowser {
    @Override
	public Viewer createViewer(Composite parent) {
        ListViewer viewer = new ListViewer(parent);
        viewer.setUseHashlookup(true);
        viewer.setContentProvider(new TestModelContentProvider());
        return viewer;
    }

    public static void main(String[] args) {
        TestList browser = new TestList();
        browser.setBlockOnOpen(true);
        browser.open(TestElement.createModel(3, 10));
    }

    @Override
	protected void viewerFillMenuBar(MenuManager mgr) {
    }
}
