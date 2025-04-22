package org.eclipse.ui.tests.views.properties.tabbed;

import junit.framework.TestCase;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.views.properties.tabbed.model.Error;
import org.eclipse.ui.tests.views.properties.tabbed.model.File;
import org.eclipse.ui.tests.views.properties.tabbed.model.Folder;
import org.eclipse.ui.tests.views.properties.tabbed.model.Information;
import org.eclipse.ui.tests.views.properties.tabbed.model.Warning;
import org.eclipse.ui.tests.views.properties.tabbed.override.OverrideTestsView;
import org.eclipse.ui.tests.views.properties.tabbed.views.TestsPerspective;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;

public class TabbedPropertySheetPageOverrideTest extends TestCase {

	private OverrideTestsView overrideTestsView;

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
				.showView(OverrideTestsView.OVERRIDE_TESTS_VIEW_ID);
		assertNotNull(view);
		assertTrue(view instanceof OverrideTestsView);
		overrideTestsView = (OverrideTestsView) view;
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		while (Display.getCurrent().readAndDispatch()) {
		}

	}

	public void test_tabForEmpty() {
		overrideTestsView.setSelection(null);

		ITabDescriptor[] tabDescriptors = overrideTestsView
				.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Empty Item", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals(1, tabDescriptors.length);
	}

	public void test_tabForError() {
		overrideTestsView.setSelection(Error.class);

		ITabDescriptor[] tabDescriptors = overrideTestsView
				.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Information", tabDescriptors[0].getLabel());//$NON-NLS-1$

		assertEquals("Warning", tabDescriptors[1].getLabel());//$NON-NLS-1$

		assertEquals("Error", tabDescriptors[2].getLabel());//$NON-NLS-1$
		assertEquals("Error", overrideTestsView.getTabbedPropertySheetPage()
				.getSelectedTab().getLabel());

		assertEquals(3, tabDescriptors.length);

	}

	public void test_tabForFile() {
		overrideTestsView.setSelection(File.class);

		ITabDescriptor[] tabDescriptors = overrideTestsView
				.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("File", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals("File", overrideTestsView.getTabbedPropertySheetPage()
				.getSelectedTab().getLabel());

		assertEquals("Folder", tabDescriptors[1].getLabel());//$NON-NLS-1$

		assertEquals(2, tabDescriptors.length);
	}

	public void test_tabForFolder() {
		overrideTestsView.setSelection(Folder.class);

		ITabDescriptor[] tabDescriptors = overrideTestsView
				.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("File", tabDescriptors[0].getLabel());//$NON-NLS-1$

		assertEquals("Folder", tabDescriptors[1].getLabel());//$NON-NLS-1$
		assertEquals("Folder", overrideTestsView.getTabbedPropertySheetPage()
				.getSelectedTab().getLabel());

		assertEquals(2, tabDescriptors.length);
	}

	public void test_tabForInformation() {
		overrideTestsView.setSelection(Information.class);

		ITabDescriptor[] tabDescriptors = overrideTestsView
				.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Information", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals("Information", overrideTestsView
				.getTabbedPropertySheetPage().getSelectedTab().getLabel());

		assertEquals("Warning", tabDescriptors[1].getLabel());//$NON-NLS-1$

		assertEquals("Error", tabDescriptors[2].getLabel());//$NON-NLS-1$

		assertEquals(3, tabDescriptors.length);
	}

	public void test_tabForWarning() {
		overrideTestsView.setSelection(Warning.class);

		ITabDescriptor[] tabDescriptors = overrideTestsView
				.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Information", tabDescriptors[0].getLabel());//$NON-NLS-1$

		assertEquals("Warning", tabDescriptors[1].getLabel());//$NON-NLS-1$
		assertEquals("Warning", overrideTestsView.getTabbedPropertySheetPage()
				.getSelectedTab().getLabel());

		assertEquals("Error", tabDescriptors[2].getLabel());//$NON-NLS-1$

		assertEquals(3, tabDescriptors.length);
	}
}
