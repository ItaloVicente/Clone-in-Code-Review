        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    /**
     * Tests the addSelectionListener method.
     */
    public void testAddSelectionListener() throws Throwable {

        fPage.addSelectionListener(this);

        clearEventState();
        SelectionProviderView view = (SelectionProviderView) fPage
                .showView(SelectionProviderView.ID);
        view.setSelection("Selection");
        assertTrue("EventReceived", eventReceived);
    }

    /**
     * Tests the removePageListener method.
     */
    public void testRemoveSelectionListener() throws Throwable {

        fPage.addSelectionListener(this);
        fPage.removeSelectionListener(this);

        clearEventState();
        SelectionProviderView view = (SelectionProviderView) fPage
                .showView(SelectionProviderView.ID);
        view.setSelection("Selection");
        assertTrue("EventReceived", !eventReceived);
    }

    /**
     * Tests getActivePage.
     */
    public void XXXtestGetSelection() throws Throwable {
        Object actualSel, sel1 = "Selection 1", sel2 = "Selection 2";

        SelectionProviderView view = (SelectionProviderView) fPage
                .showView(SelectionProviderView.ID);

        view.setSelection(sel1);
        actualSel = unwrapSelection(fPage.getSelection());
        assertEquals("Selection", sel1, actualSel);

        view.setSelection(sel2);
        actualSel = unwrapSelection(fPage.getSelection());
        assertEquals("Selection", sel2, actualSel);

        fPage.hideView(view);
        assertNull("getSelection", fPage.getSelection());
    }

    /**
     * Tests getting a selection service local to the part site
     */
    public void testLocalSelectionService() throws Throwable {
