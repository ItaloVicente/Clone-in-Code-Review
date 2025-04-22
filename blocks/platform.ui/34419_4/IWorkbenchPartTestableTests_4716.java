package org.eclipse.ui.tests.api;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.tests.harness.util.CallHistory;
import org.eclipse.ui.tests.harness.util.UITestCase;

public abstract class IWorkbenchPartTest extends UITestCase {

    protected IWorkbenchWindow fWindow;

    protected IWorkbenchPage fPage;

    public IWorkbenchPartTest(String testName) {
        super(testName);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    public void testOpenAndClose() throws Throwable {
        MockPart part = openPart(fPage);
        assertTrue(part.isSiteInitialized());
        CallHistory history = part.getCallHistory();
        assertTrue(history.verifyOrder(new String[] { "setInitializationData", "init",
                "createPartControl", "setFocus" }));

        closePart(fPage, part);
        assertTrue(history.verifyOrder(new String[] { "setInitializationData", "init",
                "createPartControl", "setFocus", "widgetDisposed", "dispose" }));
    }

    protected abstract MockPart openPart(IWorkbenchPage page) throws Throwable;

    protected abstract void closePart(IWorkbenchPage page, MockPart part)
            throws Throwable;
}

