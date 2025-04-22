package org.eclipse.jface.tests.viewers;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class ComboViewerGenericsTest extends StructuredViewerGenericsTest {
    public ComboViewerGenericsTest(String name) {
        super(name);
    }

    protected StructuredViewer<TestElement,TestElement> createViewer(Composite parent) {
        ComboViewer<TestElement,TestElement> viewer = new ComboViewer<TestElement,TestElement>(parent);
        viewer.setContentProvider(new TestModelContentProviderGenerics());
        return viewer;
    }

    protected int getItemCount() {
        TestElement first = fRootElement.getFirstChild();
        Combo list = (Combo) fViewer.testFindItem(first);
        return list.getItemCount();
    }

    protected String getItemText(int at) {
        Combo list = (Combo) fViewer.getControl();
        return list.getItem(at);
    }

    public static void main(String args[]) {
        junit.textui.TestRunner.run(ComboViewerGenericsTest.class);
    }

    public void testInsertChild() {

    }
}
