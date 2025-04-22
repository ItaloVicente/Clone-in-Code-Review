
    public void testBulkExpand() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        TestElement last = fRootElement.getLastChild();

        fTreeViewer.expandToLevel(first, 2);
        fTreeViewer.expandToLevel(first2, 2);
        fTreeViewer.expandToLevel(last, 2);
        Object[] list1 = fTreeViewer.getExpandedElements();
        setInput();
        processEvents();

        fTreeViewer.collapseAll();
        fTreeViewer.expandToLevel(first, 2);
        fTreeViewer.expandToLevel(first2, 2);
        fTreeViewer.expandToLevel(last, 2);

        Object[] list2 = fTreeViewer.getExpandedElements();

        assertEqualsArray("old and new expand state are the same", list1, list2);
    }

    public void testDeleteChildExpanded() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        fTreeViewer.expandToLevel(first2, 0);

        assertNotNull("first child is visible", fViewer.testFindItem(first2));
        first.deleteChild(first2);
        assertNull("first child is not visible", fViewer.testFindItem(first2));
    }

    public void testDeleteChildren() {
        TestElement first = fRootElement.getFirstChild();
        first.deleteChildren();
        assertTrue("no children", getItemCount(first) == 0);
    }

    public void testDeleteChildrenExpanded() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        fTreeViewer.expandToLevel(first2, 0);
        assertNotNull("first child is visible", fViewer.testFindItem(first2));

        first.deleteChildren();
        assertTrue("no children", getItemCount(first) == 0);
    }

    public void testExpand() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        assertNull("first child is not visible", fViewer.testFindItem(first2));
        fTreeViewer.expandToLevel(first2, 0);
        assertNotNull("first child is visible", fViewer.testFindItem(first2));
    }

    public void testExpandElement() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        TestElement first3 = first2.getFirstChild();
        fTreeViewer.expandToLevel(first3, 0);
        assertNotNull("first3 is visible", fViewer.testFindItem(first3));
        assertNotNull("first2 is visible", fViewer.testFindItem(first2));
    }

    public void testExpandToLevel() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        TestElement first3 = first2.getFirstChild();
        fTreeViewer.expandToLevel(3);
        processEvents();
        assertNotNull("first2 is visible", fViewer.testFindItem(first2));
        assertNotNull("first3 is visible", fViewer.testFindItem(first3));
    }

    public void testFilterExpanded() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        fTreeViewer.expandToLevel(first2, 0);

        fTreeViewer.addFilter(new TestLabelFilter());
        assertTrue("filtered count", getItemCount() == 5);
    }

    public void testInsertChildReveal() {
        TestElement first = fRootElement.getFirstChild();
        TestElement newElement = first.addChild(TestModelChange.INSERT
                | TestModelChange.REVEAL);
        assertNotNull("new sibling is visible", fViewer
                .testFindItem(newElement));
    }

    public void testInsertChildRevealSelect() {
        TestElement last = fRootElement.getLastChild();
        TestElement newElement = last.addChild(TestModelChange.INSERT
                | TestModelChange.REVEAL | TestModelChange.SELECT);
        assertNotNull("new sibling is visible", fViewer
                .testFindItem(newElement));
        assertSelectionEquals("new element is selected", newElement);
    }

    public void testInsertChildRevealSelectExpanded() {
        TestElement first = fRootElement.getFirstChild();
        TestElement newElement = first.addChild(TestModelChange.INSERT
                | TestModelChange.REVEAL | TestModelChange.SELECT);
        assertNotNull("new sibling is visible", fViewer
                .testFindItem(newElement));
        assertSelectionEquals("new element is selected", newElement);
    }

    /**
     * Regression test for 1GDN0PX: ITPUI:WIN2000 - SEVERE  - AssertionFailure when expanding Navigator
     * Problem was:
     *   - before addition, parent item had no children, and was expanded
     *   - after addition, during refresh(), updatePlus() added dummy node even though parent item was expanded
     *   - in updateChildren, it wasn't handling a dummy node
     */
    public void testRefreshWithAddedChildren() {
        TestElement parent = fRootElement.addChild(TestModelChange.INSERT);
        TestElement child = parent.addChild(TestModelChange.INSERT);
        ((AbstractTreeViewer) fViewer).setExpandedState(parent, true);
        parent.deleteChild(child);
        child = parent.addChild(TestModelChange.STRUCTURE_CHANGE);
