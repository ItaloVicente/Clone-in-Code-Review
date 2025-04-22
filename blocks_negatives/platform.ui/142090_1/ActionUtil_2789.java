    /**
     * Runs an action contribution.
     *
     * @param test the current test case
     * @param item an action contribution item
     */
    public static void runAction(TestCase test, IContributionItem item) {
        Assert.assertTrue(item instanceof ActionContributionItem);
        ((ActionContributionItem) item).getAction().run();
    }
