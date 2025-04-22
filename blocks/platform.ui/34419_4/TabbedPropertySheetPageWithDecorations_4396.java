package org.eclipse.ui.tests.views.properties.tabbed;

import junit.framework.TestCase;

import org.eclipse.swt.widgets.Display;

import org.eclipse.jface.text.IDocument;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.views.properties.tabbed.text.TextTestsLabelSection;
import org.eclipse.ui.tests.views.properties.tabbed.text.TextTestsView;
import org.eclipse.ui.tests.views.properties.tabbed.views.TestsPerspective;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;
import org.eclipse.ui.views.properties.tabbed.TabContents;

public class TabbedPropertySheetPageTextTest extends TestCase {

	private static final long TIME_OUT_TO_GET_ACTIVE_TABS= 30000; // in ms

	private TextTestsView textTestsView;

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
				.showView(TextTestsView.TEXT_TESTS_VIEW_ID);
		assertNotNull(view);
		assertTrue(view instanceof TextTestsView);
		textTestsView = (TextTestsView) view;
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		while (Display.getCurrent().readAndDispatch()) {
		}

	}

    public void test_tabForSelectedTextDisplay() {
        IDocument document = textTestsView.getViewer().getDocument();
        document.set("This is a test");
        textTestsView.getViewer().setSelectedRange(0, 14);
        
		ITabDescriptor[] tabDescriptors= waitForActiveTabs();
        assertEquals("This", tabDescriptors[0].getLabel());//$NON-NLS-1$
        assertEquals("is", tabDescriptors[1].getLabel());//$NON-NLS-1$
        assertEquals("a", tabDescriptors[2].getLabel());//$NON-NLS-1$
        assertEquals("test", tabDescriptors[3].getLabel());//$NON-NLS-1$
        assertEquals(4, tabDescriptors.length);
    }

	public void test_tabSelectedTab() {
	    IDocument document = textTestsView.getViewer().getDocument();
	    document.set("The fifth tab is selected");
	    textTestsView.getViewer().setSelectedRange(0, 26);
	    
		ITabDescriptor[] tabDescriptors= waitForActiveTabs();

	    assertEquals("The", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals("The", textTestsView.getTabbedPropertySheetPage()
				.getSelectedTab().getLabel());
	    assertEquals("selected", tabDescriptors[4].getLabel());//$NON-NLS-1$
	    
	    textTestsView.getTabbedPropertySheetPage().setSelectedTab(tabDescriptors[4].getId());
	    
	    tabDescriptors = textTestsView.getTabbedPropertySheetPage().getActiveTabs();
	    assertEquals("The", tabDescriptors[0].getLabel());//$NON-NLS-1$
	    assertEquals("selected", tabDescriptors[4].getLabel());//$NON-NLS-1$
		assertEquals("selected", textTestsView.getTabbedPropertySheetPage()
				.getSelectedTab().getLabel());
	}

	private ITabDescriptor[] waitForActiveTabs() {
		long threshold= System.currentTimeMillis() + TIME_OUT_TO_GET_ACTIVE_TABS;
		ITabDescriptor[] tabDescriptors;
		do {
			textTestsView.getSite().getShell().getDisplay().readAndDispatch();
			tabDescriptors= textTestsView.getTabbedPropertySheetPage().getActiveTabs();
		} while (tabDescriptors.length == 0 && System.currentTimeMillis() < threshold);
		assertTrue("No tab got activated", tabDescriptors.length > 0);
		return tabDescriptors;
	}

	public void test_listOfSections() {
        IDocument document = textTestsView.getViewer().getDocument();
        document.set("This is a test");
        textTestsView.getViewer().setSelectedRange(0, 14);

		waitForActiveTabs();

		TabContents tabContents= textTestsView.getTabbedPropertySheetPage().getCurrentTab();
        ISection[] sections = tabContents.getSections();
        assertEquals(1, sections.length);
        assertEquals(TextTestsLabelSection.class, sections[0].getClass());
	}
}
