    /**
     * Set the selection in the view to cause the properties view to change.
     *
     * @param selectedNodes
     *            nodes to select in the view.
     */
    private void setSelection(TreeNode[] selectedNodes) {
        StructuredSelection selection = new StructuredSelection(selectedNodes);
        decorationTestsView.getViewer().setSelection(selection, true);
    }

    /**
     * When Information node is selected, the Information tab is widest if decorations are not used.
     */
    public void test_widestLabelIndex1_WithoutDecorations() {
    	((TabbedPropertySheetPageWithDecorations)decorationTestsView.getTabbedPropertySheetPage()).useDecorations(false);
        /**
         * select Information node
         */
        setSelection(new TreeNode[] {treeNodes[0]});
        ITabDescriptor[] tabDescriptors = decorationTestsView.getTabbedPropertySheetPage().getActiveTabs();

        /**
         * First tab is Name
         */
        assertEquals("Name", tabDescriptors[0].getLabel());//$NON-NLS-1$
        /**
         * Second tab is Information
         */
        assertEquals("Information", tabDescriptors[1].getLabel());//$NON-NLS-1$
        /**
         * Third tab is Message
         */
        assertEquals("Message", tabDescriptors[2].getLabel());//$NON-NLS-1$
        /**
         * No fourth tab
         */
        assertEquals(3, tabDescriptors.length);

        /**
         * Information tab is widest
         */
        assertEquals(1, ((TabbedPropertyComposite) decorationTestsView.getTabbedPropertySheetPage().getControl()).getList().getWidestLabelIndex());
    }

    /**
     * When Information node is selected, the Name tab is widest if decorations are used.
     */
    public void test_widestLabelIndex1_WithDecorations() {
    	((TabbedPropertySheetPageWithDecorations)decorationTestsView.getTabbedPropertySheetPage()).useDecorations(true);
        /**
         * select Information node
         */
        setSelection(new TreeNode[] {treeNodes[0]});
        ITabDescriptor[] tabDescriptors = decorationTestsView.getTabbedPropertySheetPage().getActiveTabs();

        /**
         * First tab is Name
         */
        assertEquals("Name", tabDescriptors[0].getLabel());//$NON-NLS-1$
        /**
         * Second tab is Information
         */
        assertEquals("Information", tabDescriptors[1].getLabel());//$NON-NLS-1$
        /**
         * Third tab is Message
         */
        assertEquals("Message", tabDescriptors[2].getLabel());//$NON-NLS-1$
        /**
         * No fourth tab
         */
        assertEquals(3, tabDescriptors.length);

        /**
         * Name tab is widest
         */
        assertEquals(0, ((TabbedPropertyComposite) decorationTestsView.getTabbedPropertySheetPage().getControl()).getList().getWidestLabelIndex());
    }

    /**
     * When Two Information nodes are selected, the Information tab is widest if decorations are not used.
     */
    public void test_widestLabelIndex2_WithoutDecorations() {
    	((TabbedPropertySheetPageWithDecorations)decorationTestsView.getTabbedPropertySheetPage()).useDecorations(false);
        /**
         * select nodes
         */
        setSelection(new TreeNode[] {treeNodes[0], treeNodes[1]});
        ITabDescriptor[] tabDescriptors = decorationTestsView.getTabbedPropertySheetPage().getActiveTabs();

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

        /**
         * Information tab is widest
         */
        assertEquals(0, ((TabbedPropertyComposite) decorationTestsView.getTabbedPropertySheetPage().getControl()).getList().getWidestLabelIndex());
    }

    /**
     * When Two Information nodes are selected, the Message tab is widest if decorations are used.
     */
    public void test_widestLabelIndex2_WithDecorations() {
    	((TabbedPropertySheetPageWithDecorations)decorationTestsView.getTabbedPropertySheetPage()).useDecorations(true);
        /**
         * select nodes
         */
        setSelection(new TreeNode[] {treeNodes[0], treeNodes[1]});
        ITabDescriptor[] tabDescriptors = decorationTestsView.getTabbedPropertySheetPage().getActiveTabs();

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

        /**
         * Message tab is widest
         */
        assertEquals(1, ((TabbedPropertyComposite) decorationTestsView.getTabbedPropertySheetPage().getControl()).getList().getWidestLabelIndex());
    }
