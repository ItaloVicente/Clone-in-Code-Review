package org.eclipse.ui.tests.navigator;


public class InitialActivationTest extends NavigatorTestBase {

	public InitialActivationTest() {

		_navigatorInstanceId = TEST_VIEWER_INITIAL_ACTIVATION;
	}

	public void testInitialActivationExpression() throws Exception {
		assertFalse(_contentService.isActive(TEST_CONTENT_INITIAL_ACTIVATION_FALSE));
		assertTrue(_contentService.isActive(TEST_CONTENT_INITIAL_ACTIVATION_TRUE));
	}


}
