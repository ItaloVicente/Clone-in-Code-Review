    /**
     * When Error node is selected, the Message tab is widest.
     */
    public void test_widestLabelIndex2() {
        /**
         * select Error node
         */
        setSelection(new TreeNode[] {treeNodes[2]});
        ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
