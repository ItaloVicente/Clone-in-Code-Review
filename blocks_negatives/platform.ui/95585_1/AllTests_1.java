    public static Test suite() {
        return new AllTests();
    }

    public AllTests() {
        addTestSuite(ContributionItemTest.class);
        addTestSuite(ToolBarManagerTest.class);
        addTestSuite(CoolBarManagerTest.class);
        addTestSuite(MenuManagerTest.class);
    }
