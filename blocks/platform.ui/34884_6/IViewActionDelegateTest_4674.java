package org.eclipse.ui.tests.api;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.tests.SelectionProviderView;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class ISelectionServiceTest extends UITestCase implements
        ISelectionListener {
    private IWorkbenchWindow fWindow;

    private IWorkbenchPage fPage;

    private boolean eventReceived;

    private ISelection eventSelection;

    private IWorkbenchPart eventPart;

    public ISelectionServiceTest(String testName) {
        super(testName);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    public void testAddSelectionListener() throws Throwable {

        fPage.addSelectionListener(this);

        clearEventState();
        SelectionProviderView view = (SelectionProviderView) fPage
                .showView(SelectionProviderView.ID);
        view.setSelection("Selection");
        assertTrue("EventReceived", eventReceived);
    }

    public void testRemoveSelectionListener() throws Throwable {

        fPage.addSelectionListener(this);
        fPage.removeSelectionListener(this);

        clearEventState();
        SelectionProviderView view = (SelectionProviderView) fPage
                .showView(SelectionProviderView.ID);
        view.setSelection("Selection");
        assertTrue("EventReceived", !eventReceived);
    }

    public void XXXtestGetSelection() throws Throwable {
        Object actualSel, sel1 = "Selection 1", sel2 = "Selection 2";

        SelectionProviderView view = (SelectionProviderView) fPage
                .showView(SelectionProviderView.ID);

        view.setSelection(sel1);
        actualSel = unwrapSelection(fPage.getSelection());
        assertEquals("Selection", sel1, actualSel);

        view.setSelection(sel2);
        actualSel = unwrapSelection(fPage.getSelection());
        assertEquals("Selection", sel2, actualSel);

        fPage.hideView(view);
        assertNull("getSelection", fPage.getSelection());
    }
    
    public void testLocalSelectionService() throws Throwable {
		Object sel1 = "Selection 1";

		SelectionProviderView view2 = (SelectionProviderView) fPage
				.showView(SelectionProviderView.ID_2);
		SelectionProviderView view = (SelectionProviderView) fPage
				.showView(SelectionProviderView.ID);

		ISelectionService service = fWindow.getSelectionService();
		ISelectionService windowService = fWindow
				.getService(ISelectionService.class);
		ISelectionService slaveService = view2.getSite()
				.getService(ISelectionService.class);

		assertTrue(service != slaveService);
		assertEquals(service, windowService);
		assertNotNull(service);
		assertNotNull(slaveService);

		slaveService.addSelectionListener(this);
		view.setSelection(sel1);

		assertTrue("EventReceived", eventReceived);
		assertEquals("EventPart", view, eventPart);
		assertEquals("Event Selection", sel1, unwrapSelection(eventSelection));

		fPage.hideView(view2);
		clearEventState();

		view.setSelection(sel1);

		assertFalse(eventReceived);
		assertNull(eventPart);
		assertNull(eventSelection);
	}

    public void testSelectionEventWhenInactive() throws Throwable {
        Object sel1 = "Selection 1", sel2 = "Selection 2";

        fPage.addSelectionListener(this);

        SelectionProviderView view1 = (SelectionProviderView) fPage
                .showView(SelectionProviderView.ID);
        SelectionProviderView view2 = (SelectionProviderView) fPage
                .showView(SelectionProviderView.ID_2);

        clearEventState();
        view2.setSelection(sel2);
        assertTrue("EventReceived", eventReceived);
        assertEquals("EventPart", view2, eventPart);
        assertEquals("Event Selection", sel2, unwrapSelection(eventSelection));

        clearEventState();
        view1.setSelection(sel1);
        assertTrue("Unexpected selection events received", !eventReceived);
    }

    public void testSelectionEventWhenActivated() throws Throwable {
        Object sel1 = "Selection 1", sel2 = "Selection 2";

        fPage.addSelectionListener(this);

        SelectionProviderView view1 = (SelectionProviderView) fPage
                .showView(SelectionProviderView.ID);
        view1.setSelection(sel1);

        SelectionProviderView view2 = (SelectionProviderView) fPage
                .showView(SelectionProviderView.ID_2);
        view2.setSelection(sel2);

        clearEventState();
        fPage.activate(view1);
        assertTrue("EventReceived", eventReceived);
        assertEquals("EventPart", view1, eventPart);
        assertEquals("Event Selection", sel1, unwrapSelection(eventSelection));

        clearEventState();
        fPage.activate(view2);
        assertTrue("EventReceived", eventReceived);
        assertEquals("EventPart", view2, eventPart);
        assertEquals("Event Selection", sel2, unwrapSelection(eventSelection));
    }

    private Object unwrapSelection(ISelection sel) {
        if (sel instanceof IStructuredSelection) {
            IStructuredSelection struct = (IStructuredSelection) sel;
            if (struct.size() == 1)
                return struct.getFirstElement();
        }
        return null;
    }

    private void clearEventState() {
        eventReceived = false;
        eventPart = null;
        eventSelection = null;
    }

    @Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        eventReceived = true;
        eventPart = part;
        eventSelection = selection;
    }

}
