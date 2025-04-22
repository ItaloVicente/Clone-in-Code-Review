		IDocument document = textTestsView.getViewer().getDocument();
		document.set("The fifth tab is selected");
		textTestsView.getViewer().setSelectedRange(0, 26);

		ITabDescriptor[] tabDescriptors = waitForActiveTabs();

		assertEquals("The", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals("The", textTestsView.getTabbedPropertySheetPage().getSelectedTab().getLabel());
		assertEquals("selected", tabDescriptors[4].getLabel());//$NON-NLS-1$

		textTestsView.getTabbedPropertySheetPage().setSelectedTab(tabDescriptors[4].getId());

		tabDescriptors = textTestsView.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("The", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals("selected", tabDescriptors[4].getLabel());//$NON-NLS-1$
		assertEquals("selected", textTestsView.getTabbedPropertySheetPage().getSelectedTab().getLabel());
