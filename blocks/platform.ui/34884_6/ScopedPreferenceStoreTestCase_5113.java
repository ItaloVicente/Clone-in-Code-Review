package org.eclipse.ui.tests.preferences;

import org.eclipse.ui.tests.propertyPages.PropertyPageEnablementTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PreferencesTestSuite extends TestSuite {

	public static Test suite() {
		return new PreferencesTestSuite();
	}

	public PreferencesTestSuite() {
		addTest(new TestSuite(FontPreferenceTestCase.class));
		addTest(new TestSuite(DeprecatedFontPreferenceTestCase.class));
		addTest(new TestSuite(ScopedPreferenceStoreTestCase.class));
		addTest(new TestSuite(WorkingCopyPreferencesTestCase.class));
		addTest(new TestSuite(PropertyPageEnablementTest.class));
		addTest(new TestSuite(ListenerRemovalTestCase.class));
		addTest(new TestSuite(PreferencesDialogTest.class));
	}
}
