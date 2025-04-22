
package org.eclipse.ui.tests.performance;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PerformanceTestRunner extends TestSuite {

    public static Test suite() {
        return new UIPerformanceTestSetup(new PerformanceTestRunner());
    }

    public PerformanceTestRunner() {
        String className = System.getProperty("org.eclipse.ui.performance.test");
        try {
            Class clazz = Class.forName(className);
            if (TestSuite.class.isAssignableFrom(clazz))
                addTest((Test) clazz.newInstance());
            else
                addTestSuite(clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
