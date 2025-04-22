/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.internal;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.tests.api.ListView;
import org.eclipse.ui.tests.harness.util.ActionUtil;

/**
 * This class contains tests for popup menu visibility
 */
public class PopupMenuExpressionTest extends ActionExpressionTest {

    public PopupMenuExpressionTest(String testName) {
        super(testName);
    }

    /**
     * Returns the menu manager containing the actions.
     */
    @Override
	protected MenuManager getActionMenuManager(ListView view) throws Throwable {
        return view.getMenuManager();
    }

    /**
     * Tests the visibility of an action.
     */
    @Override
	protected void testAction(MenuManager mgr, String action, boolean expected)
            throws Throwable {
        if (expected) {
			assertNotNull(action, ActionUtil.getActionWithLabel(mgr, action));
		} else {
			assertNull(action, ActionUtil.getActionWithLabel(mgr, action));
		}
    }

    public void testExpressionEnabledAction() throws Throwable {
        ListView view = showListView();
        MenuManager mgr = getActionMenuManager(view);

        selectAndUpdateMenu(view, null, mgr);
        testAction(mgr, "expressionEnablementAction_v2", false);

        selectAndUpdateMenu(view, red, mgr);
        testAction(mgr, "expressionEnablementAction_v2", true);

        selectAndUpdateMenu(view, blue, mgr);
        testAction(mgr, "expressionEnablementAction_v2", false);
    }

}
