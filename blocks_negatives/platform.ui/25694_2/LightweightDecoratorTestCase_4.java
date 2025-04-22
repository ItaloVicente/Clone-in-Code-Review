        implements ILabelProviderListener {

    /**
     * Constructor for DecoratorTestCase.
     * @param testName
     */
    public LightweightDecoratorTestCase(String testName) {
        super(testName);
    }

    /**
     * Refresh the test decorator.
     */
    public void testRefreshContributor() throws CoreException {

        updated = false;
        getDecoratorManager().clearCaches();
        definition.setEnabled(true);
        getDecoratorManager().updateForEnablementChange();

        assertTrue("Got an update", updated);
        updated = false;

    }
