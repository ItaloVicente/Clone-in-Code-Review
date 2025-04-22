
package org.eclipse.ui.tests.propertysheet;

import java.util.HashMap;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.tests.SelectionProviderView;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.PropertyShowInContext;

public class NewPropertySheetHandlerTest extends AbstractPropertySheetTest {

	private TestNewPropertySheetHandler testNewPropertySheetHandler;

	public NewPropertySheetHandlerTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		testNewPropertySheetHandler = new TestNewPropertySheetHandler();
	}

	private ExecutionEvent getExecutionEvent() {
		IHandlerService handlerService = PlatformUI
				.getWorkbench().getService(IHandlerService.class);
		ICommandService commandService = PlatformUI
				.getWorkbench().getService(ICommandService.class);
		IEvaluationContext evalContext = handlerService.getCurrentState();
		Command command = commandService
				.getCommand(TestNewPropertySheetHandler.ID);
		ExecutionEvent executionEvent = new ExecutionEvent(command,
				new HashMap(), null, evalContext);
		return executionEvent;
	}

	public final void testGetShowInContextFromPropertySheet()
			throws ExecutionException, PartInitException {
		activePage.showView(IPageLayout.ID_PROP_SHEET);

		PropertyShowInContext context = testNewPropertySheetHandler
				.getShowInContext(getExecutionEvent());
		assertNotNull(context);
		assertNull(context.getSelection());
		assertNull(context.getPart());
		assertNull(context.getInput());
	}

	public final void testGetShowInContextFromAShowInSource()
			throws ExecutionException, PartInitException {
		IAdapterFactory factory = new IAdapterFactory() {
			@Override
			public Object getAdapter(Object adaptableObject, Class adapterType) {
				return new IShowInSource() {
					@Override
					public ShowInContext getShowInContext() {
						return new ShowInContext(StructuredSelection.EMPTY,
								StructuredSelection.EMPTY);
					}
				};
			}

			@Override
			public Class[] getAdapterList() {
				return new Class[] { IShowInSource.class };
			}
		};
		try {
			SelectionProviderView selectionProviderView = (SelectionProviderView) activePage
					.showView(SelectionProviderView.ID);
			selectionProviderView.setSelection(StructuredSelection.EMPTY);
			Platform.getAdapterManager().registerAdapters(factory,
					SelectionProviderView.class);

			PropertyShowInContext context = testNewPropertySheetHandler
					.getShowInContext(getExecutionEvent());
			assertNotNull(context);
			assertEquals(StructuredSelection.EMPTY, context.getSelection());
			assertEquals(StructuredSelection.EMPTY, context.getInput());
			assertEquals(selectionProviderView, context.getPart());
		} finally {
			Platform.getAdapterManager().unregisterAdapters(factory);
		}
	}

	public final void testGetShowInContextWithNoShowInSource()
			throws PartInitException, ExecutionException {
		SelectionProviderView selectionProviderView = (SelectionProviderView) activePage
				.showView(SelectionProviderView.ID);
		assertFalse(selectionProviderView instanceof IShowInSource);
		assertNull(selectionProviderView.getAdapter(IShowInSource.class));

		PropertyShowInContext context = testNewPropertySheetHandler
				.getShowInContext(getExecutionEvent());
		assertNotNull(context);
		assertNull(context.getSelection());
		assertNull(context.getInput());
		assertEquals(selectionProviderView, context.getPart());
	}

	public final void testGetShowInContextWithNoActivePart() {
		try {
			testNewPropertySheetHandler.getShowInContext(getExecutionEvent());
		} catch (ExecutionException e) {
			return;
		}
		fail("Expected ExecutionException due to no active part");
	}

	public final void testFindPropertySheetWithoutActivePart()
			throws PartInitException, ExecutionException {
		assertNull(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActivePart());

		try {
			testNewPropertySheetHandler.findPropertySheet(getExecutionEvent(),
					new PropertyShowInContext(null, StructuredSelection.EMPTY));
		} catch (ExecutionException e) {
			return;
		}
		fail("Expected ExecutionException due to no active part");
	}

	public final void testFindPropertySheetWithOtherSheetActive()
			throws PartInitException, ExecutionException {
		propertySheet = (PropertySheet) activePage
				.showView(IPageLayout.ID_PROP_SHEET);
		assertTrue(countPropertySheetViews() == 1);

		PropertySheet foundSheet = testNewPropertySheetHandler
				.findPropertySheet(getExecutionEvent(),
						new PropertyShowInContext(propertySheet,
								StructuredSelection.EMPTY));
		assertNotNull(foundSheet);
		assertNotSame(propertySheet, foundSheet);
		assertTrue(countPropertySheetViews() == 2);
	}

	public final void testFindPropertySheetWithSPVActive()
			throws PartInitException, ExecutionException {
		IViewPart showView = activePage.showView(IPageLayout.ID_PROP_SHEET);
		IViewPart spv = activePage.showView(SelectionProviderView.ID);
		assertTrue(countPropertySheetViews() == 1);

		PropertySheet foundSheet = testNewPropertySheetHandler
				.findPropertySheet(getExecutionEvent(),
						new PropertyShowInContext(spv,
								StructuredSelection.EMPTY));
		assertNotNull(foundSheet);
		assertEquals(showView, foundSheet);
		assertTrue(countPropertySheetViews() == 1);
	}

	public final void testFindPropertySheetWithPinnedPSandSPVActive()
			throws PartInitException, ExecutionException {
		PropertySheet sheet = (PropertySheet) activePage
				.showView(IPageLayout.ID_PROP_SHEET);
		sheet.setPinned(true);
		IViewPart spv = activePage.showView(SelectionProviderView.ID);
		assertTrue(countPropertySheetViews() == 1);

		PropertySheet foundSheet = testNewPropertySheetHandler
				.findPropertySheet(getExecutionEvent(),
						new PropertyShowInContext(spv,
								StructuredSelection.EMPTY));
		assertNotNull(foundSheet);
		assertNotSame(sheet, foundSheet);
		assertTrue(countPropertySheetViews() == 2);
	}

	public final void testFindPropertySheetWithUnpinnedPSandSPVActive()
			throws PartInitException, ExecutionException {
	    PropertySheetPerspectiveFactory.applyPerspective(activePage);
	    
		PropertySheet sheet = (PropertySheet) activePage
				.showView(IPageLayout.ID_PROP_SHEET);
		IViewPart showView = activePage.showView(SelectionProviderView.ID);
		PropertyShowInContext context = new PropertyShowInContext(showView,
				StructuredSelection.EMPTY);
		assertTrue(sheet.show(context));
		sheet.setPinned(true);
		assertTrue(countPropertySheetViews() == 1);

		PropertySheet foundSheet = testNewPropertySheetHandler
				.findPropertySheet(getExecutionEvent(), context);
		assertNotNull(foundSheet);
		assertEquals(sheet, foundSheet);
		assertTrue(countPropertySheetViews() == 1);
	}
}
