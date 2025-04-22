	public void test_noPropertiesAvailable() {
		TabContents tabContents = testsView.getTabbedPropertySheetPage().getCurrentTab();
		assertNull(tabContents);
		ITabDescriptor[] TabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals(0, TabDescriptors.length);
		assertEquals(-1, ((TabbedPropertyComposite) testsView.getTabbedPropertySheetPage().getControl()).getList().getWidestLabelIndex());
	}
