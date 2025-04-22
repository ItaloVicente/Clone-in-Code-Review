package org.eclipse.ui.tests.keys;

import junit.framework.Test;
import junit.framework.TestSuite;

public class KeysTestSuite extends TestSuite {

    public static Test suite() {
        return new KeysTestSuite();
    }

    public KeysTestSuite() {
    	super(KeysTestSuite.class.getName());
    	addTest(new TestSuite(BindingInteractionsTest.class));
    	addTest(new TestSuite(BindingManagerTest.class));
        addTest(new TestSuite(BindingPersistenceTest.class));
        addTest(new TestSuite(Bug36537Test.class));
        addTest(new TestSuite(Bug42024Test.class));
        addTest(new TestSuite(Bug42035Test.class));
        addTest(new TestSuite(Bug43168Test.class));
        addTest(new TestSuite(Bug43321Test.class));
        addTest(new TestSuite(Bug43538Test.class));
        addTest(new TestSuite(Bug43597Test.class));
        addTest(new TestSuite(Bug43610Test.class));
        addTest(new TestSuite(Bug43800Test.class));
        addTest(new TestSuite(KeysCsvTest.class));
        addTest(new TestSuite(Bug189167Test.class));
        addTest(new TestSuite(KeysPreferenceModelTest.class));
    }
}
