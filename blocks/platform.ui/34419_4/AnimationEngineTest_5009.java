package org.eclipse.ui.tests.internal;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.SubContributionItem;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.tests.api.ListView;
import org.eclipse.ui.tests.harness.util.ActionUtil;

public class ActionSetExpressionTest extends ActionExpressionTest {

    public ActionSetExpressionTest(String testName) {
        super(testName);
    }

    @Override
	protected MenuManager getActionMenuManager(ListView view) throws Throwable {
        fPage.showActionSet("org.eclipse.ui.tests.internal.ListElementActions");
        WorkbenchWindow win = (WorkbenchWindow) fWindow;
        IContributionItem item = win.getMenuBarManager().find(
                "org.eclipse.ui.tests.internal.ListElementMenu");
        while (item instanceof SubContributionItem) {
            item = ((SubContributionItem) item).getInnerItem();
            if (item instanceof MenuManager) {
				return (MenuManager) item;
			}
        }
        fail("Unable to find menu manager");
        return null;
    }

    @Override
	protected void testAction(MenuManager mgr, String action, boolean expected)
            throws Throwable {
        assertEquals(action, expected, ActionUtil.getActionWithLabel(mgr,
                action).isEnabled());
    }
}
