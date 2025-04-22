/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.api;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.tests.harness.util.UITestCase;

/**
 * Test the lifecycle of an action delegate.
 */
public abstract class IActionDelegateTest extends UITestCase {

    protected IWorkbenchWindow fWindow;

    protected IWorkbenchPage fPage;

    /**
     * Constructor for IActionDelegateTest
     */
    public IActionDelegateTest(String testName) {
        super(testName);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    public void testRun() throws Throwable {
        Object obj = createActionWidget();

        runAction(obj);
        MockActionDelegate delegate = getDelegate();
        assertNotNull(delegate);
        assertTrue(delegate.callHistory.verifyOrder(new String[] {
                "selectionChanged", "run" }));
    }

    public void testSelectionChanged() throws Throwable {
        Object obj = createActionWidget();
        runAction(obj);
        MockActionDelegate delegate = getDelegate();
        assertNotNull(delegate);

        delegate.callHistory.clear();
        fireSelection(obj);
        assertTrue(delegate.callHistory.contains("selectionChanged"));
    }

    /**
     * Returns the last mock action delegate which was created.
     */
    protected MockActionDelegate getDelegate() throws Throwable {
        MockActionDelegate delegate = MockActionDelegate.lastDelegate;
        assertNotNull(delegate);
        return delegate;
    }

    /**
     * Creates the widget for an action, and adds the action.
     *
     * @returns an object which will be passed to runAction and
     * fireSelection.
     */
    protected abstract Object createActionWidget() throws Throwable;

    /**
     * Adds and runs the action delegate.  Subclasses should override.
     *
     * @param obj the object returned from createActionWidget.
     */
    protected abstract void runAction(Object widget) throws Throwable;

    /**
     * Fires a selection from the source.  Subclasses should override.
     *
     * @param obj the object returned from createActionWidget.
     */
    protected abstract void fireSelection(Object widget) throws Throwable;
}

