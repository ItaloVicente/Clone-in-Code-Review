	public void test_widestLabelIndex2_WithDecorations() {
		((TabbedPropertySheetPageWithDecorations) decorationTestsView.getTabbedPropertySheetPage())
				.useDecorations(true);
		setSelection(new TreeNode[] { treeNodes[0], treeNodes[1] });
		ITabDescriptor[] tabDescriptors = decorationTestsView.getTabbedPropertySheetPage().getActiveTabs();

		assertEquals("Information", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals("Message", tabDescriptors[1].getLabel());//$NON-NLS-1$
		assertEquals(2, tabDescriptors.length);

		assertEquals(1, ((TabbedPropertyComposite) decorationTestsView.getTabbedPropertySheetPage().getControl())
				.getList().getWidestLabelIndex());
	}
