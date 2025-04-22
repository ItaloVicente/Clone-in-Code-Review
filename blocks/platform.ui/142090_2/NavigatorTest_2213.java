	public NavigatorTest(String testName) {
		super(testName);
	}

	public void testInitialPopulation() throws CoreException, PartInitException {
		createTestFile();
		showNav();

		ISelectionProvider selProv = navigator.getSite().getSelectionProvider();
		StructuredSelection sel = new StructuredSelection(testFile);
		selProv.setSelection(sel);
		assertEquals(sel.size(), ((IStructuredSelection)selProv.getSelection()).size());
