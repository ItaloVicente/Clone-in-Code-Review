	}

	public void testRemoveAtPosition_selectedElement0() {
		prepRemoveAtPositionTest();

		TableViewer viewer = (TableViewer) fViewer;
		List<TestElement> selectedElementsAfterRemove = Arrays.asList(testElement1, testElement2, testElement3);
		expectedSelection = expectedPostSelection = new StructuredSelection(selectedElementsAfterRemove);
