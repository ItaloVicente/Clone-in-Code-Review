        waitForJobs(300, 1000);
        processEvents();
        if (((AbstractTreeViewer) fViewer).getExpandedState(parent)) {
            assertNotNull("new child is visible", fViewer.testFindItem(child));
        }
    }

    /**
     * Regression test for 1GBDB5A: ITPUI:WINNT - Exception in AbstractTreeViewer update.
     * Problem was:
     *   node has child A
     *   node gets duplicate child A
     *   viewer is refreshed rather than using add for new A
     *   AbstractTreeViewer.updateChildren(...) was not properly handling it
     */
    public void testRefreshWithDuplicateChild() {
        TestElement first = fRootElement.getFirstChild();
        TestElement newElement = (TestElement) first.clone();
        fRootElement.addChild(newElement, new TestModelChange(
                TestModelChange.STRUCTURE_CHANGE, fRootElement));
        assertNotNull("new sibling is visible", fViewer
                .testFindItem(newElement));
    }

    /**
     * Regression test for Bug 3840 [Viewers] free expansion of jar happening when deleting projects (1GEV2FL)
     * Problem was:
     *   - node has children A and B
     *   - A is expanded, B is not
     *   - A gets deleted
     *   - B gets expanded because it reused A's item
     */
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

    /**
     * Regression test for Bug 26698 [Viewers] stack overflow during debug session, causing IDE to crash
     * Problem was:
     *   - node A has child A
     *   - setExpanded with A in the list caused an infinite recursion
     */
    public void testSetExpandedWithCycle() {
        TestElement first = fRootElement.getFirstChild();
        first.addChild(first, new TestModelChange(TestModelChange.INSERT,
                first, first));
        fTreeViewer.setExpandedElements(new Object[] { first });

    }

    /**
     * Test for Bug 41710 - assertion that an object may not be added to a given
     * TreeItem more than once.
     */
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
