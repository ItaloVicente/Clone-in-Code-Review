		decorationTestsView = (DecorationTestsView) view;

		IContentProvider contentProvider = decorationTestsView.getViewer().getContentProvider();
		assertTrue(contentProvider instanceof TestsViewContentProvider);
		TestsViewContentProvider viewContentProvider = (TestsViewContentProvider) contentProvider;
		treeNodes = viewContentProvider.getInvisibleRoot().getChildren();
		assertEquals(treeNodes.length, 8);
	}
