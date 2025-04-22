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
