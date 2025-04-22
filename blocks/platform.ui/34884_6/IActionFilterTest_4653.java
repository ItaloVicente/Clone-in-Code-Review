package org.eclipse.ui.tests.api;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.tests.harness.util.UITestCase;

public abstract class IActionDelegateTest extends UITestCase {

    protected IWorkbenchWindow fWindow;

    protected IWorkbenchPage fPage;

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

    protected MockActionDelegate getDelegate() throws Throwable {
        MockActionDelegate delegate = MockActionDelegate.lastDelegate;
        assertNotNull(delegate);
        return delegate;
    }

    protected abstract Object createActionWidget() throws Throwable;

    protected abstract void runAction(Object widget) throws Throwable;

    protected abstract void fireSelection(Object widget) throws Throwable;
}

