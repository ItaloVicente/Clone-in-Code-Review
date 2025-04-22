/*******************************************************************************
 * Copyright (c) 2008, 2009 Versant Corp. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 ******************************************************************************/

package org.eclipse.ui.tests.propertysheet;

import java.lang.reflect.Field;

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
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.tests.SelectionProviderView;
import org.eclipse.ui.tests.harness.util.FileUtil;
import org.eclipse.ui.tests.session.NonRestorableView;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.NewPropertySheetHandler;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.PropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetPage;

/**
 * @since 3.4
 */
public class MultiInstancePropertySheetTest extends AbstractPropertySheetTest {

	/**
	 * TestPropertySheetPage exposes certain members for testability
	 */
	private TestPropertySheetPage testPropertySheetPage;
	private SelectionProviderView selectionProviderView;

	/**
	 * An exception of an error status that has been logged by the platform.
	 */
	private Exception e;

	/**
	 * A log listener to monitor the platform's log for any error statuses. As
	 * many listeners are notified of events through a SafeRunner, errors caused
	 * by mishandling of events are not propagated back to our test methods.
	 */
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
		testPropertySheetPage = new TestPropertySheetPage();
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
		testPropertySheetPage.dispose();
		testPropertySheetPage = null;
		selectionProviderView = null;

        if (project != null) {
            FileUtil.deleteProject(project);
            project = null;
        }
	}

	/**
	 * The if the registered {@link TestPropertySheetPage} is set as the default
	 * page of the PropertySheet
	 *
	 * @throws PartInitException
	 */
	public void testDefaultPage() throws PartInitException {
		PropertySheet propertySheet = (PropertySheet) activePage
				.showView(IPageLayout.ID_PROP_SHEET);
		assertTrue(propertySheet.getCurrentPage() instanceof PropertySheetPage);
	}

	/**
	 * Test if the registered {@link TestPropertySheetPage} is set as the
	 * default page of the PropertyShecet
	 *
	 * @throws PartInitException
	 */
	public void testDefaultPageAdapter() throws PartInitException {
		PropertySheet propertySheet = (PropertySheet) activePage
				.showView(IPageLayout.ID_PROP_SHEET);
		assertTrue(propertySheet.getCurrentPage() instanceof TestPropertySheetPage);
	}

	/**
	 * Test if the PropertySheet allows multiple instances
	 *
	 * @throws PartInitException
	 */
	public void testAllowsMultiple() throws PartInitException {
		activePage.showView(IPageLayout.ID_PROP_SHEET);
		try {
			activePage.showView(IPageLayout.ID_PROP_SHEET, "aSecondaryId",
					IWorkbenchPage.VIEW_ACTIVATE);
		} catch (PartInitException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test if the PropertySheet follows selection
	 *
	 * @throws Throwable
	 */
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

	/**
	 * Test if the PropertySheet follows part events
	 *
	 * @throws Throwable
	 */
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

	/**
	 * Test if pinning works in the PropertySheet
	 *
	 * @throws Throwable
	 */
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

	/**
	 * Test if the PropertySheet unpinns if the contributing part is closed
	 *
	 * @throws Throwable
	 */
	public void testUnpinningWhenPinnedPartIsClosed() throws Throwable {
		IAction action = getPinPropertySheetAction(propertySheet);
		action.setChecked(true);

		activePage.hideView(selectionProviderView);

		assertFalse(action.isChecked());
	}

	/**
	 * Test if the PropertySheet's new handler creates a new instance
	 *
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void testNewPropertySheet() throws ExecutionException,
			NotDefinedException, NotEnabledException, NotHandledException {
		assertTrue(countPropertySheetViews() == 1);
		executeNewPropertySheetHandler();
		assertTrue(countPropertySheetViews() == 2);
	}

	/**
	 * @throws ExecutionException
	 * @throws NotDefinedException
	 * @throws NotEnabledException
	 * @throws NotHandledException
	 */
	private void executeNewPropertySheetHandler() throws ExecutionException,
			NotDefinedException, NotEnabledException, NotHandledException {

		activePage.activate(propertySheet);

		IHandlerService handlerService = PlatformUI
				.getWorkbench().getService(IHandlerService.class);
		Event event = new Event();
		handlerService.executeCommand(NewPropertySheetHandler.ID, event);
	}

	/**
	 * Test if the PropertySheet pins the parent if a second instance is opened
	 *
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void testParentIsPinned() throws ExecutionException,
			NotDefinedException, NotEnabledException, NotHandledException {
		executeNewPropertySheetHandler();

		IAction pinAction = getPinPropertySheetAction(propertySheet);
		assertTrue("Parent property sheet isn't pinned", pinAction.isChecked());
	}

	/**
	 * Test if the PropertySheet pins the parent if a second instance is opened
	 *
	 * @throws Throwable
	 */
	public void testPinningWithMultipleInstances() throws Throwable {
		executeNewPropertySheetHandler();
		testPinning();
	}

	/**
	 * Tests that pinning a property sheet ensures that the content continues to
	 * be rendered even if the original target part is not visible and is behind
	 * another part in the part stack.
	 *
	 * @throws CoreException
	 */
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

	/**
	 * Tests bug 278514. If a view that contributes to the 'Properties' view and
	 * the 'Properties' view is opened is opened in one perspective and the page
	 * then switches to another perspective where the contributing view is there
	 * but not the 'Properties' view, an NPE is thrown.
	 * <p>
	 * <ol>
	 * <li>close all perspectives</li>
	 * <li>open perspective A</li>
	 * <li>open contributing view</li>
	 * <li>switch to perspective B</li>
	 * <li>open contributing view</li>
	 * <li>open 'Properties' view</li>
	 * <li>set a selection in the contributing view so the 'Properties' view
	 * shows something</li>
	 * <li>switch back to perspective A</li>
	 * <li>NPE is thrown</li>
	 * </ol>
	 * </p>
	 *
	 * @param viewId the id of the contributing view
	 * @param standardPage <code>true</code> if the contributing view contributes properties without specifying a custom page, <code>false</code> otherwise
	 * @throws Exception if an exception occurred while switching perspectives
	 */
	private void testBug278514(String viewId, boolean standardPage) throws Exception {
		activePage.closeAllPerspectives(false, false);

		IPerspectiveDescriptor desc = activePage.getWorkbenchWindow().getWorkbench()
				.getPerspectiveRegistry().findPerspectiveWithId(IDE.RESOURCE_PERSPECTIVE_ID);
		activePage.setPerspective(desc);

		IViewReference[] viewReferences = activePage.getViewReferences();
		for (IViewReference viewReference : viewReferences) {
			if (IPageLayout.ID_PROP_SHEET.equals(viewReference.getId())) {
				activePage.hideView(viewReference);
			}
		}

		processUiEvents();
		testPropertySheetPage.dispose();

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

	/**
	 * Tests bug 278514 using a view that contributes to the 'Properties' view
	 * without using a customized page. This test uses the 'Navigator' view to
	 * achieve this.
	 *
	 * @see #testBug278514(String, boolean)
	 */
	public void testBug278514NormalProperties() throws Exception {
		testBug278514(IPageLayout.ID_RES_NAV, true);
	}

	/**
	 * Tests bug 278514 using a view that contributes to the 'Properties' view
	 * that uses a custom properties page. This test uses the 'Project Explorer'
	 * view to achieve this. The 'Project Explorer' renders content within the
	 * 'Properties' view using tabbed properties.
	 *
	 * @see #testBug278514(String, boolean)
	 */
	public void testBug278514TabbedProperties() throws Exception {
		testBug278514(IPageLayout.ID_PROJECT_EXPLORER, false);
	}

	public void testPageDispose() throws Exception {
		activePage.closeAllPerspectives(false, false);

		IPerspectiveDescriptor desc = activePage.getWorkbenchWindow().getWorkbench().getPerspectiveRegistry()
				.findPerspectiveWithId(IDE.RESOURCE_PERSPECTIVE_ID);
		activePage.setPerspective(desc);

		IViewReference[] viewReferences = activePage.getViewReferences();
		for (IViewReference viewReference : viewReferences) {
			if (IPageLayout.ID_PROP_SHEET.equals(viewReference.getId())) {
				activePage.hideView(viewReference);
			}
		}

		processUiEvents();
		testPropertySheetPage.dispose();

		propertySheet = (PropertySheet) activePage.showView(IPageLayout.ID_PROP_SHEET);
	}

	/**
	 * Tests bug 425525 using a view that contributes to the 'Properties' view
	 * without using a customized page. This test uses the 'Navigator' view to
	 * achieve this.
	 *
	 * @see #testBug425525(String, boolean)
	 */
	public void testInitialSelectionWithNormalProperties() throws Exception {
		testBug425525(IPageLayout.ID_RES_NAV, true);
	}

	/**
	 * Tests bug 425525 using a view that contributes to the 'Properties' view
	 * that uses a custom properties page. This test uses the 'Project Explorer'
	 * view to achieve this. The 'Project Explorer' renders content within the
	 * 'Properties' view using tabbed properties.
	 *
	 * @see #testBug425525(String, boolean)
	 */
	public void testInitialSelectionWithTabbedProperties() throws Exception {
		testBug425525(IPageLayout.ID_PROJECT_EXPLORER, false);
	}

	private void testBug425525(String viewId, boolean standardPage) throws Exception {
		Platform.getAdapterManager().unregisterAdapters(testPropertySheetPage, PropertySheet.class);

		activePage.closeAllPerspectives(false, false);

		IPerspectiveDescriptor desc = activePage.getWorkbenchWindow().getWorkbench().getPerspectiveRegistry()
				.findPerspectiveWithId(IDE.RESOURCE_PERSPECTIVE_ID);
		activePage.setPerspective(desc);

		IViewReference[] viewReferences = activePage.getViewReferences();
		for (IViewReference viewReference : viewReferences) {
			if (IPageLayout.ID_PROP_SHEET.equals(viewReference.getId())) {
				activePage.hideView(viewReference);
			}
		}

		processUiEvents();
		testPropertySheetPage.dispose();

		project = FileUtil.createProject("projectToSelect");

		ISelection selection = new StructuredSelection(project);

		IViewPart contributingView = activePage.showView(viewId);
		contributingView.getSite().getSelectionProvider().setSelection(selection);

		processUiEvents();

		propertySheet = (PropertySheet) activePage.showView(IPageLayout.ID_PROP_SHEET);

		IPage currentPage = propertySheet.getCurrentPage();
		if (standardPage) {
			if (currentPage instanceof PropertySheetPage) {
				PropertySheetPage psp = (PropertySheetPage) currentPage;
				Field root = PropertySheetPage.class.getDeclaredField("rootEntry");
				root.setAccessible(true);
				PropertySheetEntry pse = (PropertySheetEntry) root.get(psp);
				IPropertySheetEntry[] entries = pse.getChildEntries();
				assertTrue("The 'Properties' view should be showing the content of the contributing view ("
						+ contributingView.getTitle() + "), but shows nothing", entries.length > 0);
			} else {
				assertTrue(
						"The 'Properties' view should be showing the content of the contributing view ("
								+ contributingView.getTitle() + ") in a regular property page",
						currentPage instanceof PropertySheetPage);
			}
		} else {
			assertFalse(
					"The 'Properties' view should be showing the content of the contributing view ("
							+ contributingView.getTitle() + ") in a non-standard customiezd page",
					currentPage instanceof PropertySheetPage);
		}

	}

	private void processUiEvents() {
		while (fWorkbench.getDisplay().readAndDispatch()) {
			;
		}
	}

}
