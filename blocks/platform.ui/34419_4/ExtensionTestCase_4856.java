package org.eclipse.ui.tests.contexts;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class ContextsTestSuite extends TestSuite {

    public static final Test suite() {
        return new ContextsTestSuite();
    }

    public ContextsTestSuite() {
        addTestSuite(Bug74990Test.class);
        addTestSuite(Bug84763Test.class);
        addTestSuite(ExtensionTestCase.class);
        addTestSuite(PartContextTest.class);
    }
}
