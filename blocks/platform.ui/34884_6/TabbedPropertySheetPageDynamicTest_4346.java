
package org.eclipse.ui.tests.views.properties.tabbed;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyComposite;
import org.eclipse.ui.tests.views.properties.tabbed.decorations.TabbedPropertySheetPageWithDecorations;
import org.eclipse.ui.tests.views.properties.tabbed.decorations.views.DecorationTestsView;
import org.eclipse.ui.tests.views.properties.tabbed.views.TestsPerspective;
import org.eclipse.ui.tests.views.properties.tabbed.views.TestsViewContentProvider;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;

import junit.framework.TestCase;

public class TabbedPropertySheetPageDecorationsTest extends TestCase {

    private DecorationTestsView decorationTestsView;

    private TreeNode[] treeNodes;

    protected void setUp()
        throws Exception {
        super.setUp();

        IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow();
        assertNotNull(workbenchWindow);
        IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
        assertNotNull(workbenchPage);
        workbenchPage.closeAllPerspectives(false, false);

        PlatformUI.getWorkbench().showPerspective(
            TestsPerspective.TESTS_PERSPECTIVE_ID, workbenchWindow);

        IViewPart view = workbenchPage.showView(DecorationTestsView.DECORATION_TESTS_VIEW_ID);
        assertNotNull(view);
        assertTrue(view instanceof DecorationTestsView);
        decorationTestsView = (DecorationTestsView) view;

        IContentProvider contentProvider = decorationTestsView.getViewer()
            .getContentProvider();
        assertTrue(contentProvider instanceof TestsViewContentProvider);
        TestsViewContentProvider viewContentProvider = (TestsViewContentProvider) contentProvider;
        treeNodes = viewContentProvider.getInvisibleRoot().getChildren();
        assertEquals(treeNodes.length, 8);
    }

    protected void tearDown()
        throws Exception {
        super.tearDown();

        while (Display.getCurrent().readAndDispatch()) {
        }

        setSelection(new TreeNode[] {} );
    }

    private void setSelection(TreeNode[] selectedNodes) {
        StructuredSelection selection = new StructuredSelection(selectedNodes);
        decorationTestsView.getViewer().setSelection(selection, true);
    }

    public void test_widestLabelIndex1_WithoutDecorations() {
    	((TabbedPropertySheetPageWithDecorations)decorationTestsView.getTabbedPropertySheetPage()).useDecorations(false);
        setSelection(new TreeNode[] {treeNodes[0]});
        ITabDescriptor[] tabDescriptors = decorationTestsView.getTabbedPropertySheetPage().getActiveTabs();

        assertEquals("Name", tabDescriptors[0].getLabel());//$NON-NLS-1$
        assertEquals("Information", tabDescriptors[1].getLabel());//$NON-NLS-1$
        assertEquals("Message", tabDescriptors[2].getLabel());//$NON-NLS-1$
        assertEquals(3, tabDescriptors.length);

        assertEquals(1, ((TabbedPropertyComposite) decorationTestsView.getTabbedPropertySheetPage().getControl()).getList().getWidestLabelIndex());
    }

    public void test_widestLabelIndex1_WithDecorations() {
    	((TabbedPropertySheetPageWithDecorations)decorationTestsView.getTabbedPropertySheetPage()).useDecorations(true);
        setSelection(new TreeNode[] {treeNodes[0]});
        ITabDescriptor[] tabDescriptors = decorationTestsView.getTabbedPropertySheetPage().getActiveTabs();

        assertEquals("Name", tabDescriptors[0].getLabel());//$NON-NLS-1$
        assertEquals("Information", tabDescriptors[1].getLabel());//$NON-NLS-1$
        assertEquals("Message", tabDescriptors[2].getLabel());//$NON-NLS-1$
        assertEquals(3, tabDescriptors.length);

        assertEquals(0, ((TabbedPropertyComposite) decorationTestsView.getTabbedPropertySheetPage().getControl()).getList().getWidestLabelIndex());
    }

    public void test_widestLabelIndex2_WithoutDecorations() {
    	((TabbedPropertySheetPageWithDecorations)decorationTestsView.getTabbedPropertySheetPage()).useDecorations(false);
        setSelection(new TreeNode[] {treeNodes[0], treeNodes[1]});
        ITabDescriptor[] tabDescriptors = decorationTestsView.getTabbedPropertySheetPage().getActiveTabs();

        assertEquals("Information", tabDescriptors[0].getLabel());//$NON-NLS-1$
        assertEquals("Message", tabDescriptors[1].getLabel());//$NON-NLS-1$
        assertEquals(2, tabDescriptors.length);

        assertEquals(0, ((TabbedPropertyComposite) decorationTestsView.getTabbedPropertySheetPage().getControl()).getList().getWidestLabelIndex());
    }

    public void test_widestLabelIndex2_WithDecorations() {
    	((TabbedPropertySheetPageWithDecorations)decorationTestsView.getTabbedPropertySheetPage()).useDecorations(true);
        setSelection(new TreeNode[] {treeNodes[0], treeNodes[1]});
        ITabDescriptor[] tabDescriptors = decorationTestsView.getTabbedPropertySheetPage().getActiveTabs();

        assertEquals("Information", tabDescriptors[0].getLabel());//$NON-NLS-1$
        assertEquals("Message", tabDescriptors[1].getLabel());//$NON-NLS-1$
        assertEquals(2, tabDescriptors.length);

        assertEquals(1, ((TabbedPropertyComposite) decorationTestsView.getTabbedPropertySheetPage().getControl()).getList().getWidestLabelIndex());
    }
}
