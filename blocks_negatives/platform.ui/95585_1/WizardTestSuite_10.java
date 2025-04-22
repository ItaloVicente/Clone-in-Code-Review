    public static Test suite() {
        return new WizardTestSuite();
    }

    public WizardTestSuite() {
    	addTestSuite(ButtonAlignmentTest.class);
    	addTestSuite(WizardTest.class);
    	addTestSuite(WizardProgressMonitorTest.class);
    }
