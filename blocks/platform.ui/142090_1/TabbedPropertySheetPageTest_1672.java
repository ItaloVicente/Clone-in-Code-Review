	public void test_selectThreeMessageNodes() {
		setSelection(new TreeNode[] {treeNodes[1], treeNodes[2], treeNodes[3],});
		ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Message", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals(1, tabDescriptors.length);
	}
