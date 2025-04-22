	public static TestSuite suite() {
		TestSuite ts = new TestSuite();
		ts.addTest(new AdaptableDecoratorTestCase("testEnableDecorator"));
		ts.addTest(new AdaptableDecoratorTestCase("testDisableDecorator"));
		ts.addTest(new AdaptableDecoratorTestCase("testRefreshFullContributor"));
		ts.addTest(new AdaptableDecoratorTestCase("testRefreshLightContributor"));
		return ts;
	}

