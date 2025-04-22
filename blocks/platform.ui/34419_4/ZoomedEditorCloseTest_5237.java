package org.eclipse.ui.tests.zoom;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ZoomTestSuite extends TestSuite {
    public static Test suite() {
        return new ZoomTestSuite();
    }

    public ZoomTestSuite() {
        addTest(new TestSuite(ZoomedViewActivateTest.class));
        addTest(new TestSuite(ZoomedEditorCloseTest.class));
        addTest(new TestSuite(ZoomedViewCloseTest.class));
        addTest(new TestSuite(ShowViewTest.class));
        addTest(new TestSuite(OpenEditorTest.class));
    }
}
