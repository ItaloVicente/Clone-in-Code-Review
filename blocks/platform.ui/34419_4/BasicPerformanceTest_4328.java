
package org.eclipse.ui.tests.performance;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ActivitiesPerformanceSuite extends TestSuite {


    
    public static Test suite() {
        return new ActivitiesPerformanceSuite();
    }
    
    public ActivitiesPerformanceSuite() {
        super();
        addTest(new GenerateIdentifiersTest(10000));
    }

}
