	public void test_tabForSelectedTextDisplay() {
		IDocument document = textTestsView.getViewer().getDocument();
		document.set("This is a test");
		textTestsView.getViewer().setSelectedRange(0, 14);

		ITabDescriptor[] tabDescriptors = waitForActiveTabs();
		assertEquals("This", tabDescriptors[0].getLabel());//$NON-NLS-1$
		assertEquals("is", tabDescriptors[1].getLabel());//$NON-NLS-1$
		assertEquals("a", tabDescriptors[2].getLabel());//$NON-NLS-1$
		assertEquals("test", tabDescriptors[3].getLabel());//$NON-NLS-1$
		assertEquals(4, tabDescriptors.length);
	}
