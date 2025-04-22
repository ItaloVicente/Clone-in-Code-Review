    	assertEquals(1, fViewer.getFilters().length);
    	fViewer.getControl().dispose();
    	assertEquals(0, fViewer.getFilters().length);
    }

    public void testFilter() {
        ViewerFilter filter = new TestLabelFilter();
        fViewer.addFilter(filter);
        assertTrue("filtered count", getItemCount() == 5);
        fViewer.removeFilter(filter);
        assertTrue("unfiltered count", getItemCount() == 10);

    }

    public void testSetFilters() {
    	ViewerFilter filter = new TestLabelFilter();
