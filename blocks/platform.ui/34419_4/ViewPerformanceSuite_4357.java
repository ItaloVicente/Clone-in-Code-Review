package org.eclipse.ui.tests.performance;

import junit.framework.Test;
import junit.framework.TestSuite;

public class UIPerformanceTestSuite extends FilteredTestSuite {

    public static Test suite() {
    	return new UIPerformanceTestSetup(new UIPerformanceTestSuite());
    }

    public UIPerformanceTestSuite() {
    	super();
        addTest(new ActivitiesPerformanceSuite());
        addTest(new WorkbenchPerformanceSuite());
        addTest(new ViewPerformanceSuite());
        addTest(new EditorPerformanceSuite());
        addTest(new TestSuite(CommandsPerformanceTest.class));
		addTest(new LabelProviderTestSuite());
    }
}
