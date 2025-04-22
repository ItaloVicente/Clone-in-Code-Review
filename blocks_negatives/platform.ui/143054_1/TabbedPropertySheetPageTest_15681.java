    /**
     * When Information, Error and Warning Nodes are selected, only the Message
     * tab displays. Tests input attribute.
     */
    public void test_selectThreeMessageNodes() {
        /**
         * select nodes
         */
        setSelection(new TreeNode[] {treeNodes[1], treeNodes[2], treeNodes[3],});
        ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
        /**
         * Only tab is Message
         */
        assertEquals("Message", tabDescriptors[0].getLabel());//$NON-NLS-1$
        /**
         * No other tab
         */
        assertEquals(1, tabDescriptors.length);
    }
