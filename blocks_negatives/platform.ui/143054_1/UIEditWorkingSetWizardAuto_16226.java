        /*
         * Test page state with complete page input
         */
        setTextWidgetText(WORKING_SET_NAME_2, page);
        checkTreeItems();
        assertTrue(page.canFlipToNextPage() == false);
        assertTrue(fWizard.canFinish());
        assertNull(page.getErrorMessage());
