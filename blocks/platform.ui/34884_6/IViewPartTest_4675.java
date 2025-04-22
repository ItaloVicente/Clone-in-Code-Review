package org.eclipse.ui.tests.api;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.tests.harness.util.ActionUtil;

public class IViewActionDelegateTest extends IActionDelegateTest {

    public static String TEST_VIEW_ID = "org.eclipse.ui.tests.api.IViewActionDelegateTest";

    public IViewActionDelegateTest(String testName) {
        super(testName);
    }

    public void testInit() throws Throwable {

        testRun();

        MockActionDelegate delegate = getDelegate();
        assertNotNull(delegate);
        assertTrue(delegate.callHistory.verifyOrder(new String[] { "init",
                "selectionChanged", "run" }));
    }

    @Override
	protected Object createActionWidget() throws Throwable {
        MockViewPart view = (MockViewPart) fPage.showView(TEST_VIEW_ID);
        return view;
    }

    @Override
	protected void runAction(Object widget) throws Throwable {
        MockViewPart view = (MockViewPart) widget;
        IMenuManager mgr = view.getViewSite().getActionBars().getMenuManager();
        ActionUtil.runActionWithLabel(this, mgr, "Mock Action");
    }

    @Override
	protected void fireSelection(Object widget) throws Throwable {
        MockViewPart view = (MockViewPart) widget;
        view.fireSelection();
    }
}

