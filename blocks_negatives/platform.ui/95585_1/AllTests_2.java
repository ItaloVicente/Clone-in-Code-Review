    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new AllTests();
    }

    public AllTests() {
    	addTestSuite(DialogTest.class);
    	addTestSuite(StatusDialogTest.class);
    	addTestSuite(DialogSettingsTest.class);
    	addTestSuite(InputDialogTest.class);
        addTestSuite(TitleAreaDialogTest.class);
        addTestSuite(SafeRunnableErrorTest.class);
        addTestSuite(ProgressIndicatorStyleTest.class);
    	addTestSuite(ProgressMonitorDialogTest.class);
    }
