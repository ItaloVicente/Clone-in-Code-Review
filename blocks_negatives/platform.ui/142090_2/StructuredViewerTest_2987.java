        assertTrue(result.isEmpty());
    }

    public void testDeleteChild() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        first.deleteChild(first2);
        assertNull("first child is not visible", fViewer.testFindItem(first2));
    }

    public void testDeleteInput() {
        TestElement first = fRootElement.getFirstChild();
        TestElement firstfirst = first.getFirstChild();
        fRootElement = first;
        setInput();
        fRootElement.deleteChild(first);
        assertNull("first child is not visible", fViewer
                .testFindItem(firstfirst));
    }

    public void testDeleteSibling() {
        TestElement first = fRootElement.getFirstChild();
        assertNotNull("first child is visible", fViewer.testFindItem(first));
        fRootElement.deleteChild(first);
        assertNull("first child is not visible", fViewer.testFindItem(first));
    }

    /**
     * Tests to ensure that the viewer is properly diposed.  Includes:
     *     removal of filters
     */
    public void testDispose() {
    	assertEquals(0, fViewer.getFilters().length);
    	fViewer.addFilter(new ViewerFilter() {
