package org.eclipse.ui.tests.forms.widgets;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllWidgetsTests extends TestSuite {
	public static Test suite() {
		return new AllWidgetsTests();
	}

	public AllWidgetsTests() {
		addTestSuite(ExpandableCompositeTest.class);
		addTestSuite(FormTextModelTest.class);
	}
}
