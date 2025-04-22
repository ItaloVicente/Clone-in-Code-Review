package org.eclipse.ui.tests.api;

import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.tests.harness.util.ActionUtil;

public class IWorkbenchWindowActionDelegateTest extends IActionDelegateTest {

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

    @Override
	protected Object createActionWidget() throws Throwable {
        fPage.showActionSet("org.eclipse.ui.tests.api.MockActionSet");
        return null;
    }

    @Override
	protected void runAction(Object widget) throws Throwable {
        ActionUtil.runActionWithLabel(this, fWindow, "Mock Action");
    }

    @Override
	protected void fireSelection(Object widget) throws Throwable {
        MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
        view.fireSelection();
    }

    protected void removeAction() {
        fPage.hideActionSet("org.eclipse.ui.tests.api.MockActionSet");
    }
}

