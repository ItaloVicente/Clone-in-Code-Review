	public void test_enablesForFilter() {
		setSelection(new TreeNode[] {treeNodes[0], treeNodes[1]});
		ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Information", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals("Message", tabDescriptors[1].getLabel());//$NON-NLS-1$
		assertEquals(2, tabDescriptors.length);
	}
