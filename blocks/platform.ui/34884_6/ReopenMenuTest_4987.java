package org.eclipse.ui.tests.internal;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.tests.api.ListView;
import org.eclipse.ui.tests.harness.util.ActionUtil;

public class PopupMenuExpressionTest extends ActionExpressionTest {

    public PopupMenuExpressionTest(String testName) {
        super(testName);
    }

    @Override
	protected MenuManager getActionMenuManager(ListView view) throws Throwable {
        return view.getMenuManager();
    }

    @Override
	protected void testAction(MenuManager mgr, String action, boolean expected)
            throws Throwable {
        if (expected)
            assertNotNull(action, ActionUtil.getActionWithLabel(mgr, action));
        else
            assertNull(action, ActionUtil.getActionWithLabel(mgr, action));
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
