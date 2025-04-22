package org.eclipse.ui.tests.session;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.jface.util.Util;
import org.eclipse.ui.tests.markers.MarkersViewColumnSizeTest;
import org.eclipse.ui.tests.statushandlers.StatusHandlerConfigurationSuite;
import org.eclipse.ui.tests.statushandlers.StatusHandlingConfigurationTest;

public class SessionTests extends TestSuite {

	public static Test suite() {
		return new SessionTests();
	}

	public SessionTests() {
		addHandlerStateTests();
		addIntroTests();
		addEditorTests();
		addViewStateTests();
		addThemeTests();
		addStatusHandlingTests();
		addRestoredSessionTest();
		addWindowlessSessionTest();
	}

	private void addWindowlessSessionTest() {
		if (Util.isCocoa()) {
			Map arguments = new HashMap(2);
			arguments.put("product", null);
			arguments.put("testApplication",
					"org.eclipse.ui.tests.windowLessRcpApplication");
			WorkbenchSessionTest test = new WorkbenchSessionTest(
					"windowlessSessionTests", arguments);
			test.addTest(WindowlessSessionTest.suite());
			addTest(test);
		}
	}

	private void addStatusHandlingTests() {
		StatusHandlerConfigurationSuite test = new StatusHandlerConfigurationSuite("themeSessionTests");
		test.addTest(StatusHandlingConfigurationTest.suite());
		addTest(test);
	}

	private void addThemeTests() {
		WorkbenchSessionTest test = new WorkbenchSessionTest("themeSessionTests");
		test.addTest(ThemeStateTest.suite());
		addTest(test);
	}

	private void addRestoredSessionTest() {
		Map arguments = new HashMap(2);
		arguments.put("product", null);
		arguments.put("testApplication", "org.eclipse.ui.tests.rcpSessionApplication");
		WorkbenchSessionTest test = new WorkbenchSessionTest("introSessionTests", arguments);
		test.addTest(RestoreSessionTest.suite());
		addTest(test);
	}

	private void addEditorTests() {
		WorkbenchSessionTest test = new WorkbenchSessionTest("editorSessionTests");
		test.addTest(Bug95357Test.suite());
		addTest(test);
		
		test = new WorkbenchSessionTest("editorSessionTests");
		test.addTest(EditorWithStateTest.suite());
		addTest(test);

		test = new WorkbenchSessionTest("editorSessionTests");
		test.addTest(ArbitraryPropertiesEditorTest.suite());
		addTest(test);
	}

	private void addHandlerStateTests() {
		WorkbenchSessionTest test = new WorkbenchSessionTest("editorSessionTests");
		test.addTest(HandlerStateTest.suite());
		addTest(test);
	}

	private void addIntroTests() {
		WorkbenchSessionTest test = new WorkbenchSessionTest("introSessionTests");
		test.addTest(IntroSessionTests.suite());
		addTest(test);
	}

	private void addViewStateTests() {
		WorkbenchSessionTest test = new WorkbenchSessionTest("editorSessionTests");
		test.addTest(Bug98800Test.suite());
		addTest(test);
		
		test = new WorkbenchSessionTest("editorSessionTests");
		test.addTest(Bug108033Test.suite());
		addTest(test);
		
		test = new WorkbenchSessionTest("editorSessionTests");
		test.addTest(ArbitraryPropertiesViewTest.suite());
		addTest(test);
		
		test = new WorkbenchSessionTest("editorSessionTests");
		test.addTest(NonRestorableViewTest.suite());
		addTest(test);
		
		test = new WorkbenchSessionTest("editorSessionTests");
		test.addTest(NonRestorablePropertySheetTest.suite());
		addTest(test);

		test = new WorkbenchSessionTest("editorSessionTests");
		test.addTest(MarkersViewColumnSizeTest.suite());
		addTest(test);
	}
}
