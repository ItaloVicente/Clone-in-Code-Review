	    IDocument document = textTestsView.getViewer().getDocument();
	    document.set("The fifth tab is selected");
	    textTestsView.getViewer().setSelectedRange(0, 26);

		ITabDescriptor[] tabDescriptors= waitForActiveTabs();

	    /**
	     * First tab is "the" and is selected.
	     */
	    assertEquals("The", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals("The", textTestsView.getTabbedPropertySheetPage()
				.getSelectedTab().getLabel());
	    /**
	     * Fifth tab is "selected"
	     */
	    assertEquals("selected", tabDescriptors[4].getLabel());//$NON-NLS-1$

	    /**
	     * Set the new selected tab.
	     */
	    textTestsView.getTabbedPropertySheetPage().setSelectedTab(tabDescriptors[4].getId());

	    tabDescriptors = textTestsView.getTabbedPropertySheetPage().getActiveTabs();
	    /**
	     * First tab is "the"
	     */
	    assertEquals("The", tabDescriptors[0].getLabel());//$NON-NLS-1$
	    /**
	     * Fifth tab is "selected" and is selected.
	     */
	    assertEquals("selected", tabDescriptors[4].getLabel());//$NON-NLS-1$
		assertEquals("selected", textTestsView.getTabbedPropertySheetPage()
				.getSelectedTab().getLabel());
