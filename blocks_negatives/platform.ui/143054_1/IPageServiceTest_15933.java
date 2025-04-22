    public void testAddPageListener() throws Throwable {
        /*
         * Commented out because until test case can be updated to work
         * with new window/page/perspective implementation
         *

         fWindow.addPageListener(this);

         pageEventReceived = false;
         IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID,
         fWorkspace);
         page.close();
         assertTrue(pageEventReceived);

         fWindow.removePageListener(this);
         */
    }

    /**
     * Tests the removePageListener method.
     */
    public void XXXtestRemovePageListener() throws Throwable {

        fWindow.addPageListener(this);
        fWindow.removePageListener(this);

        pageEventReceived = false;
        IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID, getPageInput());
        page.close();
        assertTrue(!pageEventReceived);
    }

    /**
     * Tests getActivePage.
     */
    public void testGetActivePage() throws Throwable {
        /*
         * Commented out because until test case can be updated to work
         * with new window/page/perspective implementation
         *

         IWorkbenchPage page1 = fWindow.openPage(EmptyPerspective.PERSP_ID,
         fWorkspace);
         assertEquals(fWindow.getActivePage(), page1);

         IWorkbenchPage page2 = fWindow.openPage(EmptyPerspective.PERSP_ID,
         fWorkspace);
         assertEquals(fWindow.getActivePage(), page2);

         fWindow.setActivePage(page1);
         assertEquals(fWindow.getActivePage(), page1);
         fWindow.setActivePage(page2);
         assertEquals(fWindow.getActivePage(), page2);

         page1.close();
         page2.close();
         */
    }

    /**
     * Tests the addPerspectiveListener method.
     */
    public void testAddPerspectiveListener() throws Throwable {
        /*
         * Commented out because until test case can be updated to work
         * with new window/page/perspective implementation
         *

         fWindow.addPerspectiveListener(this);

         perspEventReceived = false;
         IWorkbenchPage page = fWindow.openPage(IWorkbenchConstants.DEFAULT_LAYOUT_ID,
         fWorkspace);
         page.setEditorAreaVisible(false);
         page.setEditorAreaVisible(true);
         page.close();
         assertTrue(perspEventReceived);

         fWindow.removePerspectiveListener(this);
         */
    }

    /**
     * Tests the removePerspectiveListener method.
     */
    public void XXXtestRemovePerspectiveListener() throws Throwable {

        fWindow.addPerspectiveListener(this);
        fWindow.removePerspectiveListener(this);

        perspEventReceived = false;
        IWorkbenchPage page = fWindow.openPage(IDE.RESOURCE_PERSPECTIVE_ID, getPageInput());
        page.setEditorAreaVisible(false);
        page.setEditorAreaVisible(true);
        page.close();
        assertTrue(!perspEventReceived);
    }

    /**
     * @see IPageListener#pageActivated(IWorkbenchPage)
     */
    @Override
