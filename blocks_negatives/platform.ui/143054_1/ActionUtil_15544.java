    /**
     * Runs an action identified by an id path in a
     * menu manager.
     *
     * @param test the current test case
     * @param mgr the containing menu manager
     * @param label the action label
     */
    public static void runActionUsingPath(TestCase test, IMenuManager mgr,
            String idPath) {
        IContributionItem item = mgr.findUsingPath(idPath);
        Assert.assertNotNull(item);
        runAction(test, item);
    }
