package org.eclipse.ui.tests.api;

import org.eclipse.ui.tests.harness.util.EmptyPerspective;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class IPageLayoutTest extends UITestCase {

	public IPageLayoutTest(String testName) {
		super(testName);
	}

	public void testGetDescriptor() {
		EmptyPerspective.setLastPerspective(null);
		openTestWindow(EmptyPerspective.PERSP_ID);
		assertEquals(EmptyPerspective.PERSP_ID, EmptyPerspective.getLastPerspective());
	}
}
