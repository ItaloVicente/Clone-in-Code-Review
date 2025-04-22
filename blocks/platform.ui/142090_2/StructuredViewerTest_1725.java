		assertNotNull("first child is visible", fViewer
				.testFindItem(firstfirst));
	}

	public void testSetSelection() {
		TestElement first = fRootElement.getFirstChild();
		StructuredSelection selection = new StructuredSelection(first);
		fViewer.setSelection(selection);
