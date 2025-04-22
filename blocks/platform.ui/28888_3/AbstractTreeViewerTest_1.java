	public void testExpandElementAgain() {
		TestElement first = fRootElement.getFirstChild();
		TestElement first2 = first.getFirstChild();
		TestElement first3 = first2.getFirstChild();
		fTreeViewer.expandToLevel(first3, 0);
		assertTrue("first is expanded", fTreeViewer.getExpandedState(first));
		assertTrue("first2 is expanded", fTreeViewer.getExpandedState(first2));
		assertNotNull("first3 is visible", fViewer.testFindItem(first3));

		fTreeViewer.setExpandedState(first, false);
		fTreeViewer.expandToLevel(first3, 0);
		assertTrue("first is expanded", fTreeViewer.getExpandedState(first)); // bug 54116
		assertTrue("first2 is expanded", fTreeViewer.getExpandedState(first2));
		assertNotNull("first3 is visible", fViewer.testFindItem(first3));
	}

