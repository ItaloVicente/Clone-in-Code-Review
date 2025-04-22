package org.eclipse.jface.tests.viewers;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class ComboViewerTest extends StructuredViewerTest {
    public ComboViewerTest(String name) {
        super(name);
    }

    @Override
	protected StructuredViewer createViewer(Composite parent) {
        ComboViewer viewer = new ComboViewer(parent);
        viewer.setContentProvider(new TestModelContentProvider());
        return viewer;
    }

    @Override
	protected int getItemCount() {
        TestElement first = fRootElement.getFirstChild();
        Combo list = (Combo) fViewer.testFindItem(first);
        return list.getItemCount();
    }

    @Override
	protected String getItemText(int at) {
        Combo list = (Combo) fViewer.getControl();
        return list.getItem(at);
    }

    public static void main(String args[]) {
        junit.textui.TestRunner.run(ComboViewerTest.class);
    }

    @Override
	public void testInsertChild() {

    }
}
