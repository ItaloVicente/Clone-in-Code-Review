package org.eclipse.ui.tests.views.properties.tabbed;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.model.DynamicTestsColor;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.model.DynamicTestsElement;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.model.DynamicTestsShape;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.views.DynamicTestsTreeNode;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.views.DynamicTestsView;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.views.DynamicTestsViewContentProvider;
import org.eclipse.ui.tests.views.properties.tabbed.views.TestsPerspective;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;

public class TabbedPropertySheetPageDynamicTest extends TestCase {

	private DynamicTestsView dynamicTestsView;

	private DynamicTestsTreeNode[] treeNodes;

	protected void setUp() throws Exception {
		super.setUp();

		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		assertNotNull(workbenchWindow);
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		assertNotNull(workbenchPage);
		workbenchPage.closeAllPerspectives(false, false);

		PlatformUI.getWorkbench().showPerspective(
				TestsPerspective.TESTS_PERSPECTIVE_ID, workbenchWindow);

		IViewPart view = workbenchPage
				.showView(DynamicTestsView.DYNAMIC_TESTS_VIEW_ID);
		assertNotNull(view);
		assertTrue(view instanceof DynamicTestsView);
		dynamicTestsView = (DynamicTestsView) view;

		IContentProvider contentProvider = dynamicTestsView.getViewer()
				.getContentProvider();
		assertTrue(contentProvider instanceof DynamicTestsViewContentProvider);
		DynamicTestsViewContentProvider viewContentProvider = (DynamicTestsViewContentProvider) contentProvider;
		treeNodes = (DynamicTestsTreeNode[]) viewContentProvider
				.getInvisibleRoot().getChildren();
		assertEquals(treeNodes.length, 11);
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		while (Display.getCurrent().readAndDispatch()) {
		}

		setSelection(new DynamicTestsTreeNode[] {});
	}

	private void setSelection(DynamicTestsTreeNode[] selectedNodes) {
		StructuredSelection selection = new StructuredSelection(selectedNodes);
		dynamicTestsView.getViewer().setSelection(selection, true);
	}

	public void test_BlueStaticContribution() {
		dynamicTestsView
				.setContributorId(DynamicTestsView.DYNAMIC_TESTS_VIEW_STATIC);
		select_all_blue();
	}

	public void test_BlueDynamicTabContribution() {
		dynamicTestsView
				.setContributorId(DynamicTestsView.DYNAMIC_TESTS_VIEW_DYNAMIC_TABS);
		select_all_blue();
	}

	public void test_BlueDynamicSectionContribution() {
		dynamicTestsView
				.setContributorId(DynamicTestsView.DYNAMIC_TESTS_VIEW_DYNAMIC_SECTIONS);
		select_all_blue();
	}

	public void select_all_blue() {
		List blueList = new ArrayList();
		for (int i = 0; i < treeNodes.length; i++) {
			if (DynamicTestsColor.BLUE.equals(treeNodes[i]
					.getDynamicTestsElement().getPropertyValue(
							DynamicTestsElement.ID_COLOR))) {
				blueList.add(treeNodes[i]);
			}
		}
		DynamicTestsTreeNode[] selectNodes = (DynamicTestsTreeNode[]) blueList
				.toArray(new DynamicTestsTreeNode[blueList.size()]);
		assertEquals(blueList.size(), 3);

		setSelection(selectNodes);

        ITabDescriptor[] tabDescriptors = dynamicTestsView.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Element", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals("Color", tabDescriptors[1].getLabel());//$NON-NLS-1$
        assertEquals(2, tabDescriptors.length);
	}

	public void test_TriangleStaticContribution() {
		dynamicTestsView
				.setContributorId(DynamicTestsView.DYNAMIC_TESTS_VIEW_STATIC);
		select_all_triangle();
	}

	public void test_TriangleDynamicTabContribution() {
		dynamicTestsView
				.setContributorId(DynamicTestsView.DYNAMIC_TESTS_VIEW_DYNAMIC_TABS);
		select_all_triangle();
	}

	public void test_TriangleDynamicSectionContribution() {
		dynamicTestsView
				.setContributorId(DynamicTestsView.DYNAMIC_TESTS_VIEW_DYNAMIC_SECTIONS);
		select_all_triangle();
	}

	public void select_all_triangle() {
		List triangleList = new ArrayList();
		for (int i = 0; i < treeNodes.length; i++) {
			if (DynamicTestsShape.TRIANGLE.equals(treeNodes[i]
					.getDynamicTestsElement().getPropertyValue(
							DynamicTestsElement.ID_SHAPE))) {
				triangleList.add(treeNodes[i]);
			}
		}
		DynamicTestsTreeNode[] selectNodes = (DynamicTestsTreeNode[]) triangleList
				.toArray(new DynamicTestsTreeNode[triangleList.size()]);
		assertEquals(triangleList.size(), 4);

		setSelection(selectNodes);

        ITabDescriptor[] tabDescriptors = dynamicTestsView.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Element", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals("Shape", tabDescriptors[1].getLabel());//$NON-NLS-1$
        assertEquals(2, tabDescriptors.length);
	}

	public void test_BlackTriangleStaticContribution() {
		dynamicTestsView
				.setContributorId(DynamicTestsView.DYNAMIC_TESTS_VIEW_STATIC);
		select_blackTriangle();
        ITabDescriptor[] tabDescriptors = dynamicTestsView.getTabbedPropertySheetPage().getActiveTabs();
        assertEquals(3, tabDescriptors.length);
	}

	public void test_BlackTriangleDynamicTabContribution() {
		dynamicTestsView
				.setContributorId(DynamicTestsView.DYNAMIC_TESTS_VIEW_DYNAMIC_TABS);
		select_blackTriangle();
        ITabDescriptor[] tabDescriptors = dynamicTestsView.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Black", tabDescriptors[3].getLabel());//$NON-NLS-1$
        assertEquals(4, tabDescriptors.length);
	}

	public void test_BlackTriangleDynamicSectionContribution() {
		dynamicTestsView
				.setContributorId(DynamicTestsView.DYNAMIC_TESTS_VIEW_DYNAMIC_SECTIONS);
		select_blackTriangle();
        ITabDescriptor[] tabDescriptors = dynamicTestsView.getTabbedPropertySheetPage().getActiveTabs();
        assertEquals(3, tabDescriptors.length);
	}

	public void select_blackTriangle() {
		DynamicTestsTreeNode blackTriangleNode = null;
		for (int i = 0; i < treeNodes.length; i++) {
			if (DynamicTestsColor.BLACK.equals(treeNodes[i]
					.getDynamicTestsElement().getPropertyValue(
							DynamicTestsElement.ID_COLOR))) {
				blackTriangleNode = treeNodes[i];
				break;
			}
		}
		assertNotNull(blackTriangleNode);

		setSelection(new DynamicTestsTreeNode[] { blackTriangleNode });

        ITabDescriptor[] tabDescriptors = dynamicTestsView.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Element", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals("Shape", tabDescriptors[1].getLabel());//$NON-NLS-1$
		assertEquals("Advanced", tabDescriptors[2].getLabel());//$NON-NLS-1$
	}

	public void test_RedStarStaticContribution() {
		dynamicTestsView
				.setContributorId(DynamicTestsView.DYNAMIC_TESTS_VIEW_STATIC);
		select_RedStar();
        ITabDescriptor[] tabDescriptors = dynamicTestsView.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Advanced", tabDescriptors[2].getLabel());//$NON-NLS-1$
        assertEquals(3, tabDescriptors.length);
	}

	public void test_RedStarDynamicTabContribution() {
		dynamicTestsView
				.setContributorId(DynamicTestsView.DYNAMIC_TESTS_VIEW_DYNAMIC_TABS);
		select_RedStar();
        ITabDescriptor[] tabDescriptors = dynamicTestsView.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Advanced", tabDescriptors[2].getLabel());//$NON-NLS-1$
        assertEquals(3, tabDescriptors.length);
	}

	public void test_RedStarDynamicSectionContribution() {
		dynamicTestsView
				.setContributorId(DynamicTestsView.DYNAMIC_TESTS_VIEW_DYNAMIC_SECTIONS);
		select_RedStar();
        ITabDescriptor[] tabDescriptors = dynamicTestsView.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Star", tabDescriptors[2].getLabel());//$NON-NLS-1$
		assertEquals("Advanced", tabDescriptors[3].getLabel());//$NON-NLS-1$
        assertEquals(4, tabDescriptors.length);
	}

	public void select_RedStar() {
		DynamicTestsTreeNode redStarNode = null;
		for (int i = 0; i < treeNodes.length; i++) {
			if (DynamicTestsShape.STAR.equals(treeNodes[i]
					.getDynamicTestsElement().getPropertyValue(
							DynamicTestsElement.ID_SHAPE))) {
				redStarNode = treeNodes[i];
				break;
			}
		}
		assertNotNull(redStarNode);

		setSelection(new DynamicTestsTreeNode[] { redStarNode });

        ITabDescriptor[] tabDescriptors = dynamicTestsView.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Element", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals("Color", tabDescriptors[1].getLabel());//$NON-NLS-1$
	}

}
