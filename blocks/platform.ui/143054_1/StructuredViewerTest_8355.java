		assertEquals(1, fViewer.getFilters().length);
		fViewer.getControl().dispose();
		assertEquals(0, fViewer.getFilters().length);
	}

	public void testFilter() {
		ViewerFilter filter = new TestLabelFilter();
		fViewer.addFilter(filter);
		assertEquals("filtered count", 5, getItemCount());
		fViewer.removeFilter(filter);
		assertEquals("unfiltered count", 10, getItemCount());

	}

	public void testSetFilters() {
		ViewerFilter filter = new TestLabelFilter();
