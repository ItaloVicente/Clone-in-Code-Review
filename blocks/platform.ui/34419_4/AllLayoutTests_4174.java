
package org.eclipse.ui.tests.forms;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.ui.tests.forms.layout.AllLayoutTests;
import org.eclipse.ui.tests.forms.util.AllUtilityTests;

public class AllFormsTests extends TestSuite {

	public static Test suite() {
		return new AllFormsTests();
	}

	public AllFormsTests() {
		addTest(AllLayoutTests.suite());
		addTest(AllUtilityTests.suite());
	}
}
