	public void test_sectionInformationTwoFilter() {
		setSelection(new TreeNode[] {treeNodes[1]});
		ITabDescriptor[] tabDescriptors = testsView.getTabbedPropertySheetPage().getActiveTabs();
		assertEquals("Name", tabDescriptors[0].getLabel());//$NON-NLS-1$
		TabContents tabContents = testsView.getTabbedPropertySheetPage().getCurrentTab();
		ISection[] sections = tabContents.getSections();
		assertEquals(2, sections.length);
		assertEquals(NameSection.class, sections[0].getClass());
		assertEquals(InformationTwoSection.class, sections[1].getClass());
	}
