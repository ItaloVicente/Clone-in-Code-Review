    /**
     * When One Information Node is selected, three tabs display. Tests
     * typeMapper, labelProvider, propertyCategories, afterTab attributes.
     */
    public void test_tabDisplay() {
        /**
         * select node 0 which is an Information
         */
        setSelection(new TreeNode[] {treeNodes[0]});
        ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
