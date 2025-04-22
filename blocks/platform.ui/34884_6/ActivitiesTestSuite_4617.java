package org.eclipse.ui.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.ui.tests.activities.ActivitiesTestSuite;
import org.eclipse.ui.tests.api.ApiTestSuite;
import org.eclipse.ui.tests.api.StartupTest;
import org.eclipse.ui.tests.datatransfer.DataTransferTestSuite;
import org.eclipse.ui.tests.decorators.DecoratorsTestSuite;
import org.eclipse.ui.tests.dialogs.UIAutomatedSuite;
import org.eclipse.ui.tests.encoding.EncodingTestSuite;
import org.eclipse.ui.tests.fieldassist.FieldAssistTestSuite;
import org.eclipse.ui.tests.keys.KeysTestSuite;
import org.eclipse.ui.tests.navigator.NavigatorTestSuite;
import org.eclipse.ui.tests.operations.OperationsTestSuite;
import org.eclipse.ui.tests.preferences.PreferencesTestSuite;
import org.eclipse.ui.tests.progress.ProgressTestSuite;
import org.eclipse.ui.tests.services.ServicesTestSuite;
import org.eclipse.ui.tests.themes.ThemesTestSuite;

public class UiTestSuite extends TestSuite {

	public static Test suite() {
		return new UiTestSuite();
	}

	public UiTestSuite() {
		addTest(new TestSuite(StartupTest.class));
		addTest(new UIAutomatedSuite());
		addTest(new ApiTestSuite());
		addTest(new NavigatorTestSuite());
		addTest(new DecoratorsTestSuite());
		addTest(new DataTransferTestSuite());
		addTest(new PreferencesTestSuite());
		addTest(new KeysTestSuite());
		addTest(new ActivitiesTestSuite());
		addTest(new ThemesTestSuite());
		addTest(new EncodingTestSuite());
		addTest(new OperationsTestSuite());
		addTest(new FieldAssistTestSuite());
		addTest(new ServicesTestSuite());
		addTest(new ProgressTestSuite());
	}
}
