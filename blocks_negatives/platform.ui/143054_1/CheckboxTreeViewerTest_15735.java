        fTreeViewer = new CheckboxTreeViewer(parent);
        fTreeViewer.setContentProvider(new TestModelContentProvider());
        return fTreeViewer;
    }

    public static void main(String args[]) {
        junit.textui.TestRunner.run(CheckboxTreeViewerTest.class);
    }

    public void testCheckSubtree() {
        CheckboxTreeViewer ctv = (CheckboxTreeViewer) fViewer;
        TestElement first = fRootElement.getFirstChild();
        TestElement firstfirst = first.getFirstChild();
        TestElement firstfirstfirst = firstfirst.getFirstChild();
        fTreeViewer.expandToLevel(firstfirst, 0);

        ctv.setSubtreeChecked(first, true);
        assertTrue(ctv.getChecked(firstfirst));
        ctv.setSubtreeChecked(first, false);
        assertTrue(!ctv.getChecked(firstfirst));

        assertTrue(ctv.setSubtreeChecked(firstfirstfirst, false));
        assertTrue(!ctv.getChecked(firstfirstfirst));
    }

    public void testGrayed() {
        CheckboxTreeViewer ctv = (CheckboxTreeViewer) fViewer;
        TestElement element = fRootElement.getFirstChild();

        assertTrue(ctv.getGrayedElements().length == 0);
        assertTrue(!ctv.getGrayed(element));

        ctv.setGrayed(element, true);
        assertTrue(ctv.getGrayedElements().length == 1);
        assertTrue(ctv.getGrayed(element));

        ctv.setGrayed(element, false);
        assertTrue(ctv.getGrayedElements().length == 0);
        assertTrue(!ctv.getGrayed(element));
    }

    public void testParentGrayed() {
        CheckboxTreeViewer ctv = (CheckboxTreeViewer) fViewer;
        TestElement first = fRootElement.getFirstChild();
        TestElement firstfirst = first.getFirstChild();
        TestElement firstfirstfirst = firstfirst.getFirstChild();
        ctv.expandToLevel(firstfirstfirst, 0);

        ctv.setParentsGrayed(firstfirstfirst, true);
        Object[] elements = ctv.getGrayedElements();
        assertTrue(elements.length == 3);
        for (Object element : elements) {
            assertTrue(ctv.getGrayed(element));
        }

        assertTrue(elements[0] == first);
        assertTrue(elements[1] == firstfirst);
        assertTrue(elements[2] == firstfirstfirst);
        ctv.setParentsGrayed(firstfirstfirst, false);
    }

    public void testWithoutCheckProvider() {
    	CheckboxTreeViewer ctv = (CheckboxTreeViewer)fViewer;
    	ctv.expandAll();
    	ctv.refresh();
    }

    public void testCheckProviderInvoked() {
    	CheckboxTreeViewer ctv = (CheckboxTreeViewer)fViewer;

    	TestMethodsInvokedCheckStateProvider provider = new TestMethodsInvokedCheckStateProvider();

    	ctv.setCheckStateProvider(provider);
    	assertTrue("isChecked should be invoked on a refresh", (!provider.isCheckedInvokedOn.isEmpty()));
    	assertTrue("isGrayed should be invoked on a refresh", (!provider.isGrayedInvokedOn.isEmpty()));

    	provider.reset();
    	ctv.refresh();
    	assertTrue("isChecked should be invoked on a refresh", (!provider.isCheckedInvokedOn.isEmpty()));
    	assertTrue("isGrayed should be invoked on a refresh", (!provider.isGrayedInvokedOn.isEmpty()));

    }

    public void testCheckProviderLazilyInvoked() {
    	CheckboxTreeViewer ctv = (CheckboxTreeViewer)fViewer;

    	TestMethodsInvokedCheckStateProvider provider = new TestMethodsInvokedCheckStateProvider();

    	ctv.setCheckStateProvider(provider);
    	ctv.refresh();

    	TestElement[] expected = fRootElement.getChildren();
