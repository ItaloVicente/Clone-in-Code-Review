package org.eclipse.ui.tests.views.properties.tabbed;

import junit.framework.TestCase;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyComposite;
import org.eclipse.ui.tests.views.properties.tabbed.sections.InformationTwoSection;
import org.eclipse.ui.tests.views.properties.tabbed.sections.NameSection;
import org.eclipse.ui.tests.views.properties.tabbed.views.TestsPerspective;
import org.eclipse.ui.tests.views.properties.tabbed.views.TestsView;
import org.eclipse.ui.tests.views.properties.tabbed.views.TestsViewContentProvider;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;
import org.eclipse.ui.views.properties.tabbed.TabContents;

public class TabbedPropertySheetPageTest
    extends TestCase {

    private TestsView testsView;

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

        IViewPart view = workbenchPage.showView(TestsView.TESTS_VIEW_ID);
        assertNotNull(view);
        assertTrue(view instanceof TestsView);
        testsView = (TestsView) view;

        IContentProvider contentProvider = testsView.getViewer()
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
        testsView.getViewer().setSelection(selection, true);
    }

    public void test_tabDisplay() {
        setSelection(new TreeNode[] {treeNodes[0]});
        ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
        
        assertEquals("Name", tabDescriptors[0].getLabel());//$NON-NLS-1$
        assertEquals("Information", tabDescriptors[1].getLabel());//$NON-NLS-1$
        assertEquals("Message", tabDescriptors[2].getLabel());//$NON-NLS-1$
        assertEquals(3, tabDescriptors.length);
    }

    public void test_enablesForFilter() {
        setSelection(new TreeNode[] {treeNodes[0], treeNodes[1]});
        ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
        assertEquals("Information", tabDescriptors[0].getLabel());//$NON-NLS-1$
        assertEquals("Message", tabDescriptors[1].getLabel());//$NON-NLS-1$
        assertEquals(2, tabDescriptors.length);
    }

    public void test_sectionInformationTwoFilter() {
        setSelection(new TreeNode[] {treeNodes[1]});
        ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
        assertEquals("Name", tabDescriptors[0].getLabel());//$NON-NLS-1$
        TabContents tabContents = testsView.getTabbedPropertySheetPage().getCurrentTab();
        ISection[] sections = tabContents.getSections();
        assertEquals(2, sections.length);
        assertEquals(NameSection.class, sections[0].getClass());
        assertEquals(InformationTwoSection.class, sections[1].getClass());
    }

    public void test_selectThreeMessageNodes() {
        setSelection(new TreeNode[] {treeNodes[1], treeNodes[2], treeNodes[3],});
        ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
        assertEquals("Message", tabDescriptors[0].getLabel());//$NON-NLS-1$
        assertEquals(1, tabDescriptors.length);
    }

    public void test_widestLabelIndex1() {
        setSelection(new TreeNode[] {treeNodes[0]});
        ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();

        assertEquals("Name", tabDescriptors[0].getLabel());//$NON-NLS-1$
        assertEquals("Information", tabDescriptors[1].getLabel());//$NON-NLS-1$
        assertEquals("Message", tabDescriptors[2].getLabel());//$NON-NLS-1$
        assertEquals(3, tabDescriptors.length);

        assertEquals(1, ((TabbedPropertyComposite) testsView.getTabbedPropertySheetPage().getControl()).getList().getWidestLabelIndex());
    }

    public void test_widestLabelIndex2() {
        setSelection(new TreeNode[] {treeNodes[2]});
        ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();

        assertEquals("Name", tabDescriptors[0].getLabel());//$NON-NLS-1$
        assertEquals("Error", tabDescriptors[1].getLabel());//$NON-NLS-1$
        assertEquals("Message", tabDescriptors[2].getLabel());//$NON-NLS-1$
        assertEquals(3, tabDescriptors.length);

        assertEquals(2, ((TabbedPropertyComposite) testsView.getTabbedPropertySheetPage().getControl()).getList().getWidestLabelIndex());
    }

    public void test_widestLabelIndex3() {
        setSelection(new TreeNode[] {treeNodes[3]});
        ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();

        assertEquals("Name", tabDescriptors[0].getLabel());//$NON-NLS-1$
        assertEquals("Warning", tabDescriptors[1].getLabel());//$NON-NLS-1$
        assertEquals("Message", tabDescriptors[2].getLabel());//$NON-NLS-1$
        assertEquals(3, tabDescriptors.length);

        assertEquals(1, ((TabbedPropertyComposite) testsView.getTabbedPropertySheetPage().getControl()).getList().getWidestLabelIndex());
    }

    public void test_selectThreeResourceNodes() {
        setSelection(new TreeNode[] {treeNodes[5], treeNodes[6], treeNodes[7],});
        ITabDescriptor[] TabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
        assertEquals("Resource", TabDescriptors[0].getLabel());//$NON-NLS-1$
        assertEquals(1, TabDescriptors.length);
    }

    public void test_noPropertiesAvailable() {
    	TabContents tabContents = testsView.getTabbedPropertySheetPage().getCurrentTab();
        assertNull(tabContents);
        ITabDescriptor[] TabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
        assertEquals(0, TabDescriptors.length);
        assertEquals(-1, ((TabbedPropertyComposite) testsView.getTabbedPropertySheetPage().getControl()).getList().getWidestLabelIndex());
    }

}
