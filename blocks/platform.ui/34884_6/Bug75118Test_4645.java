package org.eclipse.ui.tests.api;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug42616Test extends UITestCase {

    public Bug42616Test(String testName) {
        super(testName);
    }

    public void testErrorCondition() {
        try {
            WorkbenchPlugin.createExtension(null, null);
            fail("createExtension with nulls succeeded");
        } catch (CoreException e) {
            assertNotNull("Cause is null", e.getStatus().getException());
        } catch (Throwable t) {
            fail("Throwable not wrapped in core exception.");
        }
    }
}
