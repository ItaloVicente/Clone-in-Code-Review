		waitForJobs(300, 1000);
		processEvents();
		if (((AbstractTreeViewer) fViewer).getExpandedState(parent)) {
			assertNotNull("new child is visible", fViewer.testFindItem(child));
		}
	}

	public void testRefreshWithDuplicateChild() {
		TestElement first = fRootElement.getFirstChild();
		TestElement newElement = (TestElement) first.clone();
		fRootElement.addChild(newElement, new TestModelChange(
				TestModelChange.STRUCTURE_CHANGE, fRootElement));
		assertNotNull("new sibling is visible", fViewer
				.testFindItem(newElement));
	}

	public void testRefreshWithReusedItems() {
	}

	public void testRenameChildElement() {
		TestElement first = fRootElement.getFirstChild();
		TestElement first2 = first.getFirstChild();
		fTreeViewer.expandToLevel(first2, 0);
		assertNotNull("first child is visible", fViewer.testFindItem(first2));

		String newLabel = first2.getLabel() + " changed";
		first2.setLabel(newLabel);
		Widget widget = fViewer.testFindItem(first2);
		assertTrue(widget instanceof Item);
		assertEquals("changed label", first2.getID() + " " + newLabel,
				((Item) widget).getText());
	}

	public void testSetExpandedWithCycle() {
		TestElement first = fRootElement.getFirstChild();
		first.addChild(first, new TestModelChange(TestModelChange.INSERT,
				first, first));
		fTreeViewer.setExpandedElements(new Object[] { first });

	}

	public void testSetDuplicateChild() {
		TestElement parent = fRootElement.addChild(TestModelChange.INSERT);
		TestElement child = parent.addChild(TestModelChange.INSERT);
		int initialCount = getItemCount(parent);
		fRootElement.addChild(child, new TestModelChange(
				TestModelChange.INSERT, fRootElement, child));
		int postCount = getItemCount(parent);
		assertEquals("Same element added to a parent twice.", initialCount,
				postCount);
	}

	@Override
