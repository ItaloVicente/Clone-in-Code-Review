        assertNotNull("new sibling is visible", fViewer
                .testFindItem(newElement));
    }

    public void testInsertSiblingReveal() {
        TestElement newElement = fRootElement.addChild(TestModelChange.INSERT
                | TestModelChange.REVEAL);
        assertNotNull("new sibling is visible", fViewer
                .testFindItem(newElement));
    }

    public void testInsertSiblings() {
        TestElement[] newElements = fRootElement
                .addChildren(TestModelChange.INSERT);
        processEvents();
