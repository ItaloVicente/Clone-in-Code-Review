    /**
     * Returns the suite. This is required to use the JUnit Launcher.
     */
    public static Test suite() {
        return new KeysTestSuite();
    }

    /**
     * Construct the test suite.
     */
    public KeysTestSuite() {
    	super(KeysTestSuite.class.getName());
    	addTest(new TestSuite(BindingInteractionsTest.class));
    	addTest(new TestSuite(BindingManagerTest.class));
        addTest(new TestSuite(BindingPersistenceTest.class));
        addTest(new TestSuite(Bug36537Test.class));
        addTest(new TestSuite(Bug42024Test.class));
        addTest(new TestSuite(Bug42035Test.class));
        addTest(new TestSuite(Bug43168Test.class));
        addTest(new TestSuite(Bug43321Test.class));
        addTest(new TestSuite(Bug43538Test.class));
        addTest(new TestSuite(Bug43597Test.class));
        addTest(new TestSuite(Bug43610Test.class));
        addTest(new TestSuite(Bug43800Test.class));
        addTest(new TestSuite(KeysCsvTest.class));
        /* TODO disabled as it fails on the Mac.
         * Ctrl+S doesn't save the editor, and posting MOD1+S also doesn't seem to work.
         */
        addTest(new TestSuite(Bug189167Test.class));
        addTest(new TestSuite(KeysPreferenceModelTest.class));
    }
