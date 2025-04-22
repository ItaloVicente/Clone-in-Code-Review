		assertEquals("selected", tabDescriptors[4].getLabel());//$NON-NLS-1$

		textTestsView.getTabbedPropertySheetPage().setSelectedTab(tabDescriptors[4].getId());

		tabDescriptors = textTestsView.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("The", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals("selected", tabDescriptors[4].getLabel());//$NON-NLS-1$
