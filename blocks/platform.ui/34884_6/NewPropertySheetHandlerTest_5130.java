
package org.eclipse.ui.tests.propertysheet;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.tests.SelectionProviderView;
import org.eclipse.ui.tests.harness.util.FileUtil;
import org.eclipse.ui.tests.session.NonRestorableView;
import org.eclipse.ui.views.properties.NewPropertySheetHandler;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.PropertySheetPage;

public class MultiInstancePropertySheetTest extends AbstractPropertySheetTest {

	private TestPropertySheetPage testPropertySheetPage = new TestPropertySheetPage();
	private SelectionProviderView selectionProviderView;
	
	private Exception e;
	
	private ILogListener logListener = new ILogListener() {		
		@Override
		public void logging(IStatus status, String plugin) {
			if (status.getSeverity() == IStatus.ERROR) {
				Throwable t = status.getException();
				if (t != null) {
					e = new Exception(t);
				} else {
					e = new Exception(status.getMessage());	
				}
			}
		}
	};
	
	private IProject project;

	public MultiInstancePropertySheetTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		Platform.getAdapterManager().registerAdapters(testPropertySheetPage,
				PropertySheet.class);
		
		PropertySheetPerspectiveFactory.applyPerspective(activePage);
		
		propertySheet = (PropertySheet) activePage
				.showView(IPageLayout.ID_PROP_SHEET);

		selectionProviderView = (SelectionProviderView) activePage
				.showView(SelectionProviderView.ID);
	}



	@Override
	protected void doTearDown() throws Exception {
	    activePage.resetPerspective();         
		super.doTearDown();
		e = null;
		Platform.removeLogListener(logListener);
		Platform.getAdapterManager().unregisterAdapters(testPropertySheetPage,
				PropertySheet.class);
		testPropertySheetPage = null;
		selectionProviderView = null;

        if (project != null) {
            FileUtil.deleteProject(project);
            project = null;
        }        
	}

	public void testDefaultPage() throws PartInitException {
		PropertySheet propertySheet = (PropertySheet) activePage
				.showView(IPageLayout.ID_PROP_SHEET);
		assertTrue(propertySheet.getCurrentPage() instanceof PropertySheetPage);
	}

	public void testDefaultPageAdapter() throws PartInitException {
		PropertySheet propertySheet = (PropertySheet) activePage
				.showView(IPageLayout.ID_PROP_SHEET);
		assertTrue(propertySheet.getCurrentPage() instanceof TestPropertySheetPage);
	}

	public void testAllowsMultiple() throws PartInitException {
		activePage.showView(IPageLayout.ID_PROP_SHEET);
		try {
			activePage.showView(IPageLayout.ID_PROP_SHEET, "aSecondaryId",
					IWorkbenchPage.VIEW_ACTIVATE);
		} catch (PartInitException e) {
			fail(e.getMessage());
		}
	}

	public void testFollowsSelection() throws Throwable {
		TestPropertySheetPage firstPage = (TestPropertySheetPage) propertySheet
				.getCurrentPage();
		Object firstSelection = firstPage.getSelection();
		assertNotNull(firstSelection);

		selectionProviderView.setSelection(new Object());
		TestPropertySheetPage secondPage = (TestPropertySheetPage) propertySheet
				.getCurrentPage();

		assertNotSame("PropertySheet hasn't changed selection", firstSelection,
				secondPage.getSelection());
	}

	public void testFollowsParts() throws Throwable {
		TestPropertySheetPage firstPage = (TestPropertySheetPage) propertySheet
				.getCurrentPage();
		Object firstPart = firstPage.getPart();
		assertNotNull(firstPart);

		TestPropertySheetPage testPropertySheetPage2 = new TestPropertySheetPage();
		Platform.getAdapterManager().registerAdapters(testPropertySheetPage2,
				org.eclipse.ui.tests.session.NonRestorableView.class);
		activePage.showView(NonRestorableView.ID);

		TestPropertySheetPage secondPage = (TestPropertySheetPage) propertySheet
				.getCurrentPage();

		assertEquals(testPropertySheetPage2, secondPage);
		assertNotSame("PropertySheet hasn't changed selection", firstPart,
				secondPage.getSelection());
		Platform.getAdapterManager().unregisterAdapters(testPropertySheetPage2,
				org.eclipse.ui.tests.session.NonRestorableView.class);
	}

	public void testPinning() throws Throwable {
		IAction action = getPinPropertySheetAction(propertySheet);
		action.setChecked(true);

		TestPropertySheetPage firstPage = (TestPropertySheetPage) propertySheet
				.getCurrentPage();
		assertNotNull(firstPage);
		Object firstSelection = firstPage.getSelection();
		assertNotNull(firstSelection);
		IWorkbenchPart firstPart = firstPage.getPart();
		assertNotNull(firstPart);

		selectionProviderView.setSelection(new Object());
		TestPropertySheetPage testPropertySheetPage2 = new TestPropertySheetPage();
		Platform.getAdapterManager().registerAdapters(testPropertySheetPage2,
				org.eclipse.ui.tests.session.NonRestorableView.class);
		activePage.showView(NonRestorableView.ID);

		TestPropertySheetPage secondPage = (TestPropertySheetPage) propertySheet
				.getCurrentPage();
		assertEquals("PropertySheet has changed page", firstPage, secondPage);
		assertEquals("PropertySheetPage has changed selection", firstSelection,
				secondPage.getSelection());
		assertEquals("PropertySheetPage has changed part", firstPart,
				secondPage.getPart());
		Platform.getAdapterManager().unregisterAdapters(testPropertySheetPage2,
				org.eclipse.ui.tests.session.NonRestorableView.class);
	}

	public void testUnpinningWhenPinnedPartIsClosed() throws Throwable {
		IAction action = getPinPropertySheetAction(propertySheet);
		action.setChecked(true);

		activePage.hideView(selectionProviderView);

		assertFalse(action.isChecked());
	}

	public void testNewPropertySheet() throws ExecutionException,
			NotDefinedException, NotEnabledException, NotHandledException {
		assertTrue(countPropertySheetViews() == 1);
		executeNewPropertySheetHandler();
		assertTrue(countPropertySheetViews() == 2);
	}

	private void executeNewPropertySheetHandler() throws ExecutionException,
			NotDefinedException, NotEnabledException, NotHandledException {

		activePage.activate(propertySheet);

		IHandlerService handlerService = PlatformUI
				.getWorkbench().getService(IHandlerService.class);
		Event event = new Event();
		handlerService.executeCommand(NewPropertySheetHandler.ID, event);
	}

	public void testParentIsPinned() throws ExecutionException,
			NotDefinedException, NotEnabledException, NotHandledException {
		executeNewPropertySheetHandler();

		IAction pinAction = getPinPropertySheetAction(propertySheet);
		assertTrue("Parent property sheet isn't pinned", pinAction.isChecked());
	}

	public void testPinningWithMultipleInstances() throws Throwable {
		executeNewPropertySheetHandler();
		testPinning();
	}

	public void testBug268676HidingPinnedTargetPart() throws CoreException {
		IPerspectiveDescriptor desc = activePage.getWorkbenchWindow().getWorkbench()
				.getPerspectiveRegistry().findPerspectiveWithId(IDE.RESOURCE_PERSPECTIVE_ID);
		activePage.setPerspective(desc);
		activePage.hideView(selectionProviderView);
		propertySheet = (PropertySheet) activePage.showView(IPageLayout.ID_PROP_SHEET);
		
        project = FileUtil.createProject("projectToSelect");
        ISelection selection = new StructuredSelection(project);
		
		IViewPart navigator = activePage.showView(IPageLayout.ID_RES_NAV);        
        navigator.getSite().getSelectionProvider().setSelection(selection);

		assertTrue("The 'Properties' view should render the content of the 'Navigator' in a regular property sheet page",
				propertySheet.getCurrentPage() instanceof PropertySheetPage);

		IViewPart projectExplorer = activePage.showView(IPageLayout.ID_PROJECT_EXPLORER);        
        projectExplorer.getSite().getSelectionProvider().setSelection(selection);
		
		assertFalse("The 'Navigator' should be hidden behind the 'Project Explorer'",
				activePage.isPartVisible(navigator));
		assertTrue("The 'Project Explorer' should be visible in front of the 'Navigator'",
				activePage.isPartVisible(projectExplorer));

		assertFalse("The 'Properties' view should be showing the content of the 'Project Explorer' in a tabbed property sheet, not a regular one",
				propertySheet.getCurrentPage() instanceof PropertySheetPage);
		
		IAction action = getPinPropertySheetAction(propertySheet);
		action.setChecked(true);
		
		activePage.activate(navigator);

		assertFalse("The 'Properties' view should still be on the content of the 'Project Explorer' rendering a tabbed property sheet",
				propertySheet.getCurrentPage() instanceof PropertySheetPage);
	}
	
	private void testBug278514(String viewId, boolean standardPage) throws Exception {
		activePage.closeAllPerspectives(false, false);

		IPerspectiveDescriptor desc = activePage.getWorkbenchWindow().getWorkbench()
				.getPerspectiveRegistry().findPerspectiveWithId(IDE.RESOURCE_PERSPECTIVE_ID);
		activePage.setPerspective(desc);
		
		IViewReference[] viewReferences = activePage.getViewReferences();
		for (int i = 0; i < viewReferences.length; i++) {
			if (IPageLayout.ID_PROP_SHEET.equals(viewReferences[i].getId())) {
				activePage.hideView(viewReferences[i]);
			}
		}

		activePage.showView(viewId);
		
		PropertySheetPerspectiveFactory.applyPerspective(activePage);
		
		propertySheet = (PropertySheet) activePage.showView(IPageLayout.ID_PROP_SHEET);
		
        project = FileUtil.createProject("projectToSelect");
        ISelection selection = new StructuredSelection(project);

		IViewPart contributingView = activePage.showView(viewId);
        contributingView.getSite().getSelectionProvider().setSelection(selection);

        if (standardPage) {
    		assertTrue("The 'Properties' view should be showing the content of the contributing view (" + contributingView.getTitle() + ") in a regular property page",
    				propertySheet.getCurrentPage() instanceof PropertySheetPage);	
        } else {
    		assertFalse("The 'Properties' view should be showing the content of the contributing view (" + contributingView.getTitle() + ") in a non-standard customiezd page",
    				propertySheet.getCurrentPage() instanceof PropertySheetPage);        	
        }
		
		Platform.addLogListener(logListener);
		activePage.setPerspective(desc);
		
		if (e != null) {
			throw e;
		}
	}

	public void testBug278514NormalProperties() throws Exception {
		testBug278514(IPageLayout.ID_RES_NAV, true);
	}

	public void testBug278514TabbedProperties() throws Exception {
		testBug278514(IPageLayout.ID_PROJECT_EXPLORER, false);
	}
}
