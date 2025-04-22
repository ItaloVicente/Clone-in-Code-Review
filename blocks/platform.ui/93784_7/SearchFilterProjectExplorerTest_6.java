package org.eclipse.ui.tests.navigator;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class SearchFilterHiddenTest extends SearchFilterTestBase {

	public SearchFilterHiddenTest() {
		_navigatorInstanceId = TEST_VIEWER_SEARCHFILTER_HIDDEN;
	}

	@Test
	public void testToolItemHiddden() {
		assertNull(findToolbarItem());
	}
}
