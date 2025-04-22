    /**
     * When Information node is selected, the Information tab is widest.
     */
    public void test_widestLabelIndex1() {
        /**
         * select Information node
         */
        setSelection(new TreeNode[] {treeNodes[0]});
        ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
