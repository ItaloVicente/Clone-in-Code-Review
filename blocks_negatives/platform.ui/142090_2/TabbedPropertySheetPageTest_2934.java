    /**
     * When File, Folder and Project Nodes are selected, only the Resource tab
     * displays. Tests input attribute.
     */
    public void test_selectThreeResourceNodes() {
        /**
         * select nodes
         */
        setSelection(new TreeNode[] {treeNodes[5], treeNodes[6], treeNodes[7],});
        ITabDescriptor[] TabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
        /**
         * Only tab is Resource
         */
        assertEquals("Resource", TabDescriptors[0].getLabel());//$NON-NLS-1$
        /**
         * No other tab
         */
        assertEquals(1, TabDescriptors.length);
    }
