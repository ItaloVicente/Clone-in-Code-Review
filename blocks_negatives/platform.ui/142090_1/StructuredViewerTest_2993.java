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
