		assertEquals(1, fViewer.getFilters().length);
		fViewer.getControl().dispose();
		assertEquals(0, fViewer.getFilters().length);
	}

	public void testFilter() {
		ViewerFilter filter = new TestLabelFilter();
		fViewer.addFilter(filter);
