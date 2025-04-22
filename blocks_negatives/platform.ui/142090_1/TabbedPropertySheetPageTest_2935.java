    /**
     * When the view first comes up, there is no properties so the "Properties
     * are not available." banner is displayed. Tests null selection in a
     * viewer.
     */
    public void test_noPropertiesAvailable() {
    	TabContents tabContents = testsView.getTabbedPropertySheetPage().getCurrentTab();
        assertNull(tabContents);
        ITabDescriptor[] TabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
        assertEquals(0, TabDescriptors.length);
        /**
         * widestLabelIndex should be -1
         */
        assertEquals(-1, ((TabbedPropertyComposite) testsView.getTabbedPropertySheetPage().getControl()).getList().getWidestLabelIndex());
    }
