package org.eclipse.ui.tests.forms.util;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllUtilityTests extends TestSuite {

	public static Test suite() {
		return new AllUtilityTests();
	}

	public AllUtilityTests() {
		addTestSuite(FormImagesTests.class);
		addTestSuite(FormFontsTests.class);
		addTestSuite(FormColorsTests.class);
		addTestSuite(FormToolkitTest.class);
	}
}
