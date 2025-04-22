        TestElement first = fRootElement.getFirstChild();
        ((TableViewer) fViewer).getControl().update();
        assertNull("changed sibling is not visible", fViewer
                .testFindItem(first));
        fViewer.refresh();
        ((TableViewer) fViewer).getControl().update();
        assertNotNull("changed sibling is not visible", fViewer
                .testFindItem(first));
