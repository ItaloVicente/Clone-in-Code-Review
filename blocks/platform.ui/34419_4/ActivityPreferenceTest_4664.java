package org.eclipse.ui.tests.activities;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ActivitiesTestSuite extends TestSuite {
    public static Test suite() {
        return new ActivitiesTestSuite();
    }

    public ActivitiesTestSuite() {
      	addTest(new TestSuite(ImagesTest.class));
		addTest(new TestSuite(UtilTest.class));
        addTest(new TestSuite(StaticTest.class));
        addTest(new TestSuite(DynamicTest.class));
        addTest(new TestSuite(PersistanceTest.class));
        addTest(new TestSuite(ActivityPreferenceTest.class));
        addTest(new TestSuite(MenusTest.class));
        addTest(new TestSuite(PatternUtilTest.class));
    }
}
