    /**
     * Runs the first action found in a menu manager with a
     * particular label.
     *
     * @param test the current test case
     * @param mgr the containing menu manager
     * @param label the action label
     */
    public static void runActionWithLabel(TestCase test, IMenuManager mgr,
            String label) {
        IContributionItem[] items = mgr.getItems();
