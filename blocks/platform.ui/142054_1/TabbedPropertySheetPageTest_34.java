	public void test_selectThreeResourceNodes() {
		setSelection(new TreeNode[] { treeNodes[5], treeNodes[6], treeNodes[7], });
		ITabDescriptor[] TabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Resource", TabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals(1, TabDescriptors.length);
	}
