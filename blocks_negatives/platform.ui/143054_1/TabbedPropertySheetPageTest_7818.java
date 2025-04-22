    /**
     * When Two Information Node is selected, only two tabs display. Tests
     * enablesFor attribute.
     */
    public void test_enablesForFilter() {
        /**
         * select nodes
         */
        setSelection(new TreeNode[] {treeNodes[0], treeNodes[1]});
        ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
        /**
         * First tab is Information
         */
        assertEquals("Information", tabDescriptors[0].getLabel());//$NON-NLS-1$
        /**
         * Second tab is Message
         */
        assertEquals("Message", tabDescriptors[1].getLabel());//$NON-NLS-1$
        /**
         * No other tab
         */
        assertEquals(2, tabDescriptors.length);
    }
