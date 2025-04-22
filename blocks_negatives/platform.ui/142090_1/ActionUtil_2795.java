    /**
     * Returns the first action found in a menu manager with a
     * particular label.
     *
     * @param mgr the containing menu manager
     * @param label the action label
     * @return the first action with the label, or <code>null</code>
     * 		if it is not found.
     */
    public static IAction getActionWithLabel(IMenuManager mgr, String label) {
        IContributionItem[] items = mgr.getItems();
