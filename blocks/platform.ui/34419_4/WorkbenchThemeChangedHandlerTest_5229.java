package org.eclipse.ui.tests.themes;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ThemesTestSuite extends TestSuite {

    public static Test suite() {
        return new ThemesTestSuite();
    }

    public ThemesTestSuite() {
        addTest(new TestSuite(ThemeAPITest.class));
        addTest(new TestSuite(JFaceThemeTest.class));
        addTest(new TestSuite(WorkbenchThemeChangedHandlerTest.class));
        addTest(new TestSuite(ThemeRegistryModifiedHandlerTest.class));
        addTest(new TestSuite(StylingPreferencesHandlerTest.class));
    }
}
