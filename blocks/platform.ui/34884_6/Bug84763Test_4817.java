package org.eclipse.ui.tests.contexts;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.contexts.EnabledSubmission;
import org.eclipse.ui.contexts.IContext;
import org.eclipse.ui.contexts.IWorkbenchContextSupport;
import org.eclipse.ui.tests.harness.util.UITestCase;

public final class Bug74990Test extends UITestCase {

    public Bug74990Test(final String name) {
        super(name);
    }

    public final void testPartIdSubmission() throws PartInitException {
        final String testContextId = "org.eclipse.ui.tests.contexts.Bug74990";
        final IWorkbenchContextSupport contextSupport = fWorkbench
                .getContextSupport();
        final IContext testContext = contextSupport.getContextManager()
                .getContext(testContextId);

        final EnabledSubmission testSubmission = new EnabledSubmission(
                "org.eclipse.ui.tests.api.MockViewPart", null, null,
                testContextId);
        contextSupport.addEnabledSubmission(testSubmission);

        try {
            assertTrue("The MockViewPart context should not be enabled",
                    !testContext.isEnabled());

            final IWorkbenchPage page = openTestWindow().getActivePage();
            final IViewPart openedView = page
                    .showView("org.eclipse.ui.tests.api.MockViewPart");
            page.activate(openedView);
            while (fWorkbench.getDisplay().readAndDispatch())
                ;
            assertTrue("The MockViewPart context should be enabled",
                    testContext.isEnabled());

            page.hideView(openedView);
            while (fWorkbench.getDisplay().readAndDispatch())
                ;
            assertTrue("The MockViewPart context should not be enabled",
                    !testContext.isEnabled());

        } finally {
            contextSupport.removeEnabledSubmission(testSubmission);
        }

    }
}
