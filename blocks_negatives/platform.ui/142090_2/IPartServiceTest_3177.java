        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    private IWorkbenchPartReference getRef(IWorkbenchPart part) {
    	return fPage.getReference(part);
    }

    /**
     * Tests the addPartListener method on IWorkbenchPage's part service.
     */
    public void testAddPartListenerToPage() throws Throwable {
        fPage.addPartListener(partListener);
        fPage.addPartListener(partListener2);

        clearEventState();
        MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
        assertTrue(history.verifyOrder(new String[] { "partOpened",
                "partActivated" }));
        assertEquals(view, eventPart);
        assertTrue(history2.verifyOrder(new String[] { "partOpened",
                "partVisible", "partActivated" }));
        assertEquals(getRef(view), eventPartRef);

        clearEventState();
        fPage.hideView(view);
        assertTrue(history.verifyOrder(new String[] { "partDeactivated",
                "partClosed" }));
        assertEquals(view, eventPart);
        assertTrue(history2.verifyOrder(new String[] { "partDeactivated",
                "partHidden", "partClosed" }));
        assertEquals(getRef(view), eventPartRef);

        fPage.removePartListener(partListener);
        fPage.removePartListener(partListener2);
    }

    public void testLocalPartService() throws Throwable {
    	IPartService service = fWindow
