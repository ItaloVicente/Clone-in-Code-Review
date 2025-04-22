    /**
     * When text is selected, there is one tab for each selected word.
     */
    public void test_tabForSelectedTextDisplay() {
        IDocument document = textTestsView.getViewer().getDocument();
        document.set("This is a test");
        textTestsView.getViewer().setSelectedRange(0, 14);

		ITabDescriptor[] tabDescriptors= waitForActiveTabs();
        /**
         * First tab is "This"
         */
        assertEquals("This", tabDescriptors[0].getLabel());//$NON-NLS-1$
        /**
         * Second tab is "is"
         */
        assertEquals("is", tabDescriptors[1].getLabel());//$NON-NLS-1$
        /**
         * Third tab is "a"
         */
        assertEquals("a", tabDescriptors[2].getLabel());//$NON-NLS-1$
        /**
         * Third tab is "test"
         */
        assertEquals("test", tabDescriptors[3].getLabel());//$NON-NLS-1$
        /**
         * No fifth tab
         */
        assertEquals(4, tabDescriptors.length);
    }
