        assertNotNull("new sibling is visible", fViewer
                .testFindItem(newElement));
        assertSelectionEquals("new element is selected", newElement);
    }

    public void testInsertSiblingWithFilterFiltered() {
        fViewer.addFilter(new TestLabelFilter());
        TestElement newElement = new TestElement(fModel, fRootElement);
        newElement.setLabel("name-111");
        fRootElement.addChild(newElement, new TestModelChange(
                TestModelChange.INSERT | TestModelChange.REVEAL
                        | TestModelChange.SELECT, fRootElement, newElement));
        assertNull("new sibling is not visible", fViewer
                .testFindItem(newElement));
        assertTrue(getItemCount() == 5);
    }

    public void testInsertSiblingWithFilterNotFiltered() {
        fViewer.addFilter(new TestLabelFilter());
        TestElement newElement = new TestElement(fModel, fRootElement);
        newElement.setLabel("name-222");
        fRootElement.addChild(newElement, new TestModelChange(
                TestModelChange.INSERT | TestModelChange.REVEAL
                        | TestModelChange.SELECT, fRootElement, newElement));
        assertNotNull("new sibling is visible", fViewer
                .testFindItem(newElement));
        assertTrue(getItemCount() == 6);
    }

    public void testInsertSiblingWithSorter() {
        fViewer.setComparator(new TestLabelComparator());
        TestElement newElement = new TestElement(fModel, fRootElement);
        newElement.setLabel("name-9999");
        fRootElement.addChild(newElement, new TestModelChange(
                TestModelChange.INSERT | TestModelChange.REVEAL
                        | TestModelChange.SELECT, fRootElement, newElement));
        String newLabel = newElement.toString();
        assertEquals("sorted first", newLabel, getItemText(0));
        assertSelectionEquals("new element is selected", newElement);
    }

    public void testLabelProvider() {
        fViewer.setLabelProvider(getTestLabelProvider());
        TestElement first = fRootElement.getFirstChild();
        String newLabel = providedString(first);
        assertEquals("rendered label", newLabel, getItemText(0));
    }

    /**
     * @return IBaseLabelProvder used in this test
     */
    public IBaseLabelProvider getTestLabelProvider() {
        return new TestLabelProvider();
    }

    public void testLabelProviderStateChange() {
        TestLabelProvider provider = new TestLabelProvider();
        fViewer.setLabelProvider(provider);
        provider.setSuffix("added suffix");
        TestElement first = fRootElement.getFirstChild();
        String newLabel = providedString(first);
        assertEquals("rendered label", newLabel, getItemText(0));
    }

    public void testRename() {
        TestElement first = fRootElement.getFirstChild();
        String newLabel = first.getLabel() + " changed";
        first.setLabel(newLabel);
        assertEquals("changed label", first.getID() + " " + newLabel,
                getItemText(0));
    }

    public void testRenameWithFilter() {
        fViewer.addFilter(new TestLabelFilter());
        TestElement first = fRootElement.getFirstChild();
        assertNull("changed sibling is not visible", fViewer
                .testFindItem(first));
        fViewer.refresh();
        assertNotNull("changed sibling is not visible", fViewer
                .testFindItem(first));
    }

    public void testRenameWithLabelProvider() {
        if (fViewer instanceof TableViewer
                || fViewer instanceof TableTreeViewer) {
