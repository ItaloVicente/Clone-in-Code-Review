    /**
     * When Warning node is selected, the Warning tab is widest.
     */
    public void test_widestLabelIndex3() {
        /**
         * select Warning node
         */
        setSelection(new TreeNode[] {treeNodes[3]});
        ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
