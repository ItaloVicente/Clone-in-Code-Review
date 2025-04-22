		assertNotNull("new sibling is visible", fViewer
				.testFindItem(newElement));
		assertSelectionEquals("new element is selected", newElement);
	}

	public void testInsertSiblingWithFilterFiltered() {
		fViewer.addFilter(new TestLabelFilter());
		TestElement newElement = new TestElement(fModel, fRootElement);
		newElement.setLabel("name-111");
		fRootElement.addChild(newElement, new TestModelChange(
				TestModelChange.INSERT | TestModelChange.REVEAL
						| TestModelChange.SELECT, fRootElement, newElement));
		assertNull("new sibling is not visible", fViewer
				.testFindItem(newElement));
