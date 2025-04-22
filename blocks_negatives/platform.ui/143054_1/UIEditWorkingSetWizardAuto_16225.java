        /*
         * Test page state with partial page input
         */
        setTextWidgetText("", page);
        assertTrue(page.canFlipToNextPage() == false);
        assertTrue(fWizard.canFinish() == false);
        assertNotNull(page.getErrorMessage());
