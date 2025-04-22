		TestElement first = fRootElement.getFirstChild();
		first.setLabel("name-1111"); // should disappear
		((TableViewer) fViewer).getControl().update();
		assertNull("changed sibling is not visible", fViewer
				.testFindItem(first));
		first.setLabel("name-2222"); // should reappear
		fViewer.refresh();
		((TableViewer) fViewer).getControl().update();
		assertNotNull("changed sibling is not visible", fViewer
				.testFindItem(first));
