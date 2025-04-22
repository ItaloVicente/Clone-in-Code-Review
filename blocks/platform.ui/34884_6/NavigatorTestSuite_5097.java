package org.eclipse.ui.tests.navigator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PartInitException;

public class NavigatorTest extends AbstractNavigatorTest {

    public NavigatorTest(String testName) {
        super(testName);
    }

    public void testInitialPopulation() throws CoreException, PartInitException {
        createTestFile();
        showNav();

        ISelectionProvider selProv = navigator.getSite().getSelectionProvider();
        StructuredSelection sel = new StructuredSelection(testFile);
        selProv.setSelection(sel);
        assertEquals(sel.size(), ((IStructuredSelection)selProv.getSelection()).size());
        assertEquals(sel.getFirstElement(), ((IStructuredSelection)selProv.getSelection()).getFirstElement());
    }

    public void testFileAddition() throws CoreException, PartInitException {
        createTestFolder(); // create the project and folder before the Navigator is shown
        showNav();
        createTestFile(); // create the file after the Navigator is shown

        ISelectionProvider selProv = navigator.getSite().getSelectionProvider();
        StructuredSelection sel = new StructuredSelection(testFile);
        selProv.setSelection(sel);
        assertEquals(sel.size(), ((IStructuredSelection)selProv.getSelection()).size());
        assertEquals(sel.getFirstElement(), ((IStructuredSelection)selProv.getSelection()).getFirstElement());
    }

}
