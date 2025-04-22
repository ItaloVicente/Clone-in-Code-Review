    /**
     * When Two Information Node is selected, two section displayed on Name tab.
     * Tests filter, afterSection attribute.
     */
    public void test_sectionInformationTwoFilter() {
        /**
         * select nodes
         */
        setSelection(new TreeNode[] {treeNodes[1]});
        ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
        /**
         * First tab is Information
         */
        assertEquals("Name", tabDescriptors[0].getLabel());//$NON-NLS-1$
        TabContents tabContents = testsView.getTabbedPropertySheetPage().getCurrentTab();
        /**
         * the tab has two sections.
         */
        ISection[] sections = tabContents.getSections();
        assertEquals(2, sections.length);
        assertEquals(NameSection.class, sections[0].getClass());
        assertEquals(InformationTwoSection.class, sections[1].getClass());
    }
