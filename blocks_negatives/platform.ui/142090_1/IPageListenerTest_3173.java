        fWindow.removePageListener(this);
        super.doTearDown();
    }

    /**
     * Tests the pageOpened method.
     */
    public void testPageOpened() throws Throwable {
        /*
         * Commented out because until test case can be updated to work
         * with new window/page/perspective implementation
         *

         eventsReceived = 0;
         IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID,
         fWorkspace);
         assertEquals(eventsReceived, OPEN|ACTIVATE);

         page.close();
         */
    }

    /**
     * Tests the pageClosed method.
     */
    public void testPageClosed() throws Throwable {
        /*
         * Commented out because until test case can be updated to work
         * with new window/page/perspective implementation
         *

         IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID,
         fWorkspace);

         eventsReceived = 0;
         pageMask = page;
         page.close();
         assertEquals(eventsReceived, CLOSE);
         */
    }

    /**
     * Tests the pageActivated method.
     */
    public void testPageActivate() throws Throwable {
        /*
         * Commented out because until test case can be updated to work
         * with new window/page/perspective implementation
         *

         IWorkbenchPage page1 = fWindow.openPage(EmptyPerspective.PERSP_ID,
         fWorkspace);
         IWorkbenchPage page2 = fWindow.openPage(EmptyPerspective.PERSP_ID,
         fWorkspace);

         eventsReceived = 0;
         pageMask = page1;
         fWindow.setActivePage(page1);
         assertEquals(eventsReceived, ACTIVATE);

         eventsReceived = 0;
         pageMask = page2;
         fWindow.setActivePage(page2);
         assertEquals(eventsReceived, ACTIVATE);

         page1.close();
         page2.close();
         */
    }

    /**
     * @see IPageListener#pageActivated(IWorkbenchPage)
     */
    @Override
