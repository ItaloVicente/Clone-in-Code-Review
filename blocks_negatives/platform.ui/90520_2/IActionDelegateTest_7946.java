/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.api;

import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.handlers.IActionCommandMappingService;
import org.eclipse.ui.tests.harness.util.ActionUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

/**
 * Test the lifecycle of an action delegate.
 */
public class IActionBarsTest extends UITestCase {

    protected IWorkbenchWindow fWindow;

    protected IWorkbenchPage fPage;

    private class MockAction extends Action {
        public boolean hasRun = false;

        public MockAction() {
            super();
        }

        @Override
		public void run() {
            hasRun = true;
        }
    }

    /**
     * Constructor for IActionDelegateTest
     */
    public IActionBarsTest(String testName) {
        super(testName);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    public void testGetMenuManager() throws Throwable {

        IViewPart part = fPage.showView(MockViewPart.ID);
        IActionBars bars = part.getViewSite().getActionBars();
        assertNotNull(bars);
        IMenuManager mgr = bars.getMenuManager();
        assertNotNull(mgr);
    }

    public void testGetStatusLineManager() throws Throwable {

        IViewPart part = fPage.showView(MockViewPart.ID);
        IActionBars bars = part.getViewSite().getActionBars();
        assertNotNull(bars);
        IStatusLineManager mgr = bars.getStatusLineManager();
        assertNotNull(mgr);
    }

    public void testGetToolBarManager() throws Throwable {

        IViewPart part = fPage.showView(MockViewPart.ID);
        IActionBars bars = part.getViewSite().getActionBars();
        assertNotNull(bars);
        IToolBarManager mgr = bars.getToolBarManager();
        assertNotNull(mgr);
    }

    public void testGetGlobalActionHandler() throws Throwable {

        IViewPart part = fPage.showView(MockViewPart.ID);
        IActionBars bars = part.getViewSite().getActionBars();
        assertNotNull(bars);

        assertNull(bars.getGlobalActionHandler(IWorkbenchActionConstants.CUT));
        assertNull(bars.getGlobalActionHandler(IWorkbenchActionConstants.COPY));
        assertNull(bars.getGlobalActionHandler(IWorkbenchActionConstants.UNDO));

        MockAction cut = new MockAction();
        MockAction copy = new MockAction();
        MockAction undo = new MockAction();

        bars.setGlobalActionHandler(IWorkbenchActionConstants.CUT, cut);
        bars.setGlobalActionHandler(IWorkbenchActionConstants.COPY, copy);
        bars.setGlobalActionHandler(IWorkbenchActionConstants.UNDO, undo);
        bars.updateActionBars();

        assertEquals(cut, bars
                .getGlobalActionHandler(IWorkbenchActionConstants.CUT));
        assertEquals(copy, bars
                .getGlobalActionHandler(IWorkbenchActionConstants.COPY));
        assertEquals(undo, bars
                .getGlobalActionHandler(IWorkbenchActionConstants.UNDO));
    }

        public void testSetGlobalActionHandler() throws Throwable {

	        IViewPart part = fPage.showView(MockViewPart.ID);
	        IActionBars bars = part.getViewSite().getActionBars();
	        assertNotNull(bars);

	        MockAction cut = new MockAction();
	        MockAction copy = new MockAction();
	        MockAction undo = new MockAction();

	        bars.setGlobalActionHandler(IWorkbenchActionConstants.CUT, cut);
	        bars.setGlobalActionHandler(IWorkbenchActionConstants.COPY, copy);
	        bars.setGlobalActionHandler(IWorkbenchActionConstants.UNDO, undo);
	        bars.updateActionBars();

	        cut.hasRun = copy.hasRun = undo.hasRun = false;

	        runMatchingCommand(fWindow, ActionFactory.CUT.getId());

	        ActionUtil.runActionUsingPath(this, fWindow,
	                IWorkbenchActionConstants.M_EDIT + '/'
	                        + IWorkbenchActionConstants.UNDO);
	        assertTrue(cut.hasRun);
	        assertTrue(!copy.hasRun);
	        assertTrue(undo.hasRun);

	        fPage.showView(MockViewPart.ID2);
	        cut.hasRun = copy.hasRun = undo.hasRun = false;
	        runMatchingCommand(fWindow, ActionFactory.CUT.getId());
	        ActionUtil.runActionUsingPath(this, fWindow,
	                IWorkbenchActionConstants.M_EDIT + '/'
	                        + IWorkbenchActionConstants.UNDO);
	        assertTrue(!cut.hasRun);
	        assertTrue(!copy.hasRun);
	        assertTrue(!undo.hasRun);

	        fPage.activate(part);
	        cut.hasRun = copy.hasRun = undo.hasRun = false;
	        runMatchingCommand(fWindow, ActionFactory.CUT.getId());
	        ActionUtil.runActionUsingPath(this, fWindow,
	                IWorkbenchActionConstants.M_EDIT + '/'
	                        + IWorkbenchActionConstants.UNDO);
	        assertTrue(cut.hasRun);
	        assertTrue(!copy.hasRun);
	        assertTrue(undo.hasRun);
	    }

    private void runMatchingCommand(IWorkbenchWindow window, String actionId) {
    	IHandlerService hs = window.getService(IHandlerService.class);
    	IActionCommandMappingService ms = window.getService(IActionCommandMappingService.class);
    	String commandId = ms.getCommandId(actionId);
    	assertNotNull(commandId);
    	try {
			hs.executeCommand(commandId, null);
    	} catch (NotHandledException e) {
    	} catch (NotEnabledException e) {
		} catch (Exception e) {
			fail("Failed to run " + commandId, e);
		}
    }
}

