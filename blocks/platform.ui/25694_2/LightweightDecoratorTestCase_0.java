		implements ILabelProviderListener {

	public LightweightDecoratorTestCase(String testName) {
		super(testName);
	}

	public void testRefreshContributor() {
		updated = false;
		getDecoratorManager().clearCaches();
		definition.setEnabled(true);
		getDecoratorManager().updateForEnablementChange();

		assertTrue("Got an update", updated);
		updated = false;

	}
