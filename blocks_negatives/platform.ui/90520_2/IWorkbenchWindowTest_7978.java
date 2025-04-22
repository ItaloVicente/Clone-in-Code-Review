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

import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.tests.harness.util.ActionUtil;

/**
 * Tests the lifecycle for a window action delegate.
 */
public class IWorkbenchWindowActionDelegateTest extends IActionDelegateTest {

    /**
     * Constructor for IWorkbenchWindowActionDelegateTest
     */
    public IWorkbenchWindowActionDelegateTest(String testName) {
        super(testName);
    }

    public void testInit() throws Throwable {
        testRun();

        MockActionDelegate delegate = getDelegate();
        assertNotNull(delegate);
        assertTrue(delegate.callHistory.verifyOrder(new String[] { "init",
                "selectionChanged", "run" }));
    }


    /**
     * Regression test for bug 81422.  Tests to ensure that dispose() is only
     * called once if the delegate implements both IWorkbenchWindowActionDelegate
     * and IActionDelegate2.
     */
    public void XXXtestDisposeWorkbenchWindowActionDelegateBug81422() {
        String id = MockWorkbenchWindowActionDelegate.SET_ID;
        fPage.showActionSet(id);
        MockWorkbenchWindowActionDelegate mockWWinActionDelegate = MockWorkbenchWindowActionDelegate.lastDelegate;
        Object mockAsObject = mockWWinActionDelegate;
        assertTrue(mockAsObject instanceof IActionDelegate2);
        assertTrue(mockAsObject instanceof IWorkbenchWindowActionDelegate);
        mockWWinActionDelegate.callHistory.clear();
        fPage.close();
    	assertTrue(mockWWinActionDelegate.callHistory.contains("dispose"));
    	assertFalse(mockWWinActionDelegate.callHistory.verifyOrder(new String[] {"dispose", "dispose"}));
    }

    /**
     * @see IActionDelegateTest#createActionWidget()
     */
    @Override
	protected Object createActionWidget() throws Throwable {
        fPage.showActionSet("org.eclipse.ui.tests.api.MockActionSet");
        return null;
    }

    /**
     * @see IActionDelegateTest#runAction()
     */
    @Override
	protected void runAction(Object widget) throws Throwable {
        ActionUtil.runActionWithLabel(this, fWindow, "Mock Action");
    }

    /**
     * @see IActionDelegateTest#fireSelection()
     */
    @Override
	protected void fireSelection(Object widget) throws Throwable {
        MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
        view.fireSelection();
    }

    /**
     * Removes the action.
     */
    protected void removeAction() {
        fPage.hideActionSet("org.eclipse.ui.tests.api.MockActionSet");
    }
}

