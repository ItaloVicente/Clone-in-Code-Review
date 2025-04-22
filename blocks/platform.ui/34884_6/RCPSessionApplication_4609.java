
package org.eclipse.jface.tests.wizards;

import junit.framework.Test;
import junit.framework.TestSuite;

public class WizardTestSuite extends TestSuite {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new WizardTestSuite();
    }

    public WizardTestSuite() {
    	addTestSuite(ButtonAlignmentTest.class);
    	addTestSuite(WizardTest.class);
    	addTestSuite(WizardProgressMonitorTest.class);
    }
}
