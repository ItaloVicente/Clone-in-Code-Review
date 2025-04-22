
package org.eclipse.ui.tests.markers;

import org.eclipse.ui.tests.harness.util.UITestCase;
import org.eclipse.ui.views.markers.internal.MarkerSupportRegistry;

public class MarkerSupportRegistryTests extends UITestCase {

	public MarkerSupportRegistryTests(String testName) {
		super(testName);
	}

	public void testMarkerCategories() {
		doTestCategory("org.eclipse.ui.tests.categoryTestMarker");
		doTestCategory("org.eclipse.ui.tests.testmarker");
		doTestCategory("org.eclipse.ui.tests.testmarker2");
	}

	private void doTestCategory(String string) {
		String category = MarkerSupportRegistry.getInstance().getCategory(
				string);
		assertFalse("No Category for" + string, category == null);
		assertTrue("Wrong Category for" + string, category.equals("Test Markers"));

	}

}
