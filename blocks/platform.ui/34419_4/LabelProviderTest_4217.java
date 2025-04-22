package org.eclipse.ui.tests.navigator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class InitialActivationTest extends NavigatorTestBase {

	public InitialActivationTest() {

		_navigatorInstanceId = TEST_VIEWER_INITIAL_ACTIVATION;
	}

	@Test
	public void testInitialActivationExpression() throws Exception {
		assertFalse(_contentService.isActive(TEST_CONTENT_INITIAL_ACTIVATION_FALSE));
		assertTrue(_contentService.isActive(TEST_CONTENT_INITIAL_ACTIVATION_TRUE));
	}


}
