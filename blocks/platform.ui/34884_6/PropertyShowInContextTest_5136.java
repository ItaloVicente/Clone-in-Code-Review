package org.eclipse.ui.tests.propertysheet;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PropertySheetTestSuite extends TestSuite {

    public static Test suite() {
        return new PropertySheetTestSuite();
    }

    public PropertySheetTestSuite() {
        addTest(new TestSuite(PropertyShowInContextTest.class));
        addTest(new TestSuite(MultiInstancePropertySheetTest.class));
        addTest(new TestSuite(ShowInPropertySheetTest.class));
        addTest(new TestSuite(NewPropertySheetHandlerTest.class));
        addTest(new TestSuite(PropertySheetAuto.class));
        addTest(new TestSuite(ComboBoxPropertyDescriptorTest.class));
    }
}
