package org.eclipse.jface.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends TestSuite {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new AllTests();
    }

    public AllTests() {
        addTest(new org.eclipse.jface.tests.action.AllTests());
        addTest(new org.eclipse.jface.tests.dialogs.AllTests());
        addTest(new org.eclipse.jface.tests.images.AllTests());
        addTest(new org.eclipse.jface.tests.viewers.AllTests());
        addTest(new org.eclipse.jface.tests.layout.AllTests());
        addTest(new org.eclipse.jface.tests.preferences.AllTests());
        addTest(new org.eclipse.jface.tests.wizards.WizardTestSuite());
        addTest(new org.eclipse.jface.tests.labelProviders.DecoratingLabelProviderTests());
        addTest(new org.eclipse.jface.tests.fieldassist.FieldAssistTestSuite());
        addTest(new org.eclipse.jface.tests.window.AllTests());
    }
}
