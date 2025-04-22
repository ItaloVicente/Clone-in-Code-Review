package org.eclipse.ui.tests.adaptable;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AdaptableTestSuite extends TestSuite {

    public static Test suite() {
        return new AdaptableTestSuite();
    }

    public AdaptableTestSuite() {
        addTest(AdaptableDecoratorTestCase.suite());
        addTest(new TestSuite(MarkerImageProviderTest.class));
        addTest(new TestSuite(WorkingSetTestCase.class));
        addTest(new TestSuite(SelectionAdapterTest.class));
    }

}
