/*******************************************************************************
 * Copyright (c) 2000, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
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

/**
 * Tests the ISelectionService class.
 */
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

    /**
     * Tests the addSelectionListener method.
     */
    public void testAddSelectionListener() throws Throwable {

        fPage.addSelectionListener(this);

        clearEventState();
        SelectionProviderView view = (SelectionProviderView) fPage
                .showView(SelectionProviderView.ID);
        view.setSelection("Selection");
        assertTrue("EventReceived", eventReceived);
    }

    /**
     * Tests the removePageListener method.
     */
    public void testRemoveSelectionListener() throws Throwable {

        fPage.addSelectionListener(this);
        fPage.removeSelectionListener(this);

        clearEventState();
        SelectionProviderView view = (SelectionProviderView) fPage
                .showView(SelectionProviderView.ID);
        view.setSelection("Selection");
        assertTrue("EventReceived", !eventReceived);
    }

    /**
     * Tests getActivePage.
     */
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

    /**
     * Tests getting a selection service local to the part site
     */
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

    /**
	 * Test event firing for inactive parts. In this scenario the event should
	 * not be fired.
	 */
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

    /**
     * Test event firing when activated.
     */
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

    /**
     * Unwrap a selection.
     */
    private Object unwrapSelection(ISelection sel) {
        if (sel instanceof IStructuredSelection) {
            IStructuredSelection struct = (IStructuredSelection) sel;
            if (struct.size() == 1) {
				return struct.getFirstElement();
			}
        }
        return null;
    }

    /**
     * Clear the event state.
     */
    private void clearEventState() {
        eventReceived = false;
        eventPart = null;
        eventSelection = null;
    }

    /*
     * @see ISelectionListener#selectionChanged(IWorkbenchPart, ISelection)
     */
    @Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        eventReceived = true;
        eventPart = part;
        eventSelection = selection;
    }

}
