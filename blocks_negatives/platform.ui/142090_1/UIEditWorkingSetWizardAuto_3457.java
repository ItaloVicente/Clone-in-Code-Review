        /*
         * Verify that correct working set edit page is displayed
         */
        assertTrue(page.getClass() == fDefaultEditPage.getClass());
        /*
         * Test initial page state
         */
        assertTrue(page.canFlipToNextPage() == false);
        assertTrue(fWizard.canFinish() == false);
        assertNull(page.getErrorMessage());
        /*
         * Test page state with preset page input
         */
        IWorkingSetManager workingSetManager = fWorkbench
                .getWorkingSetManager();
        IWorkingSet workingSet = workingSetManager.createWorkingSet(
                WORKING_SET_NAME_1, new IAdaptable[] { p1, f2 });
        ((WorkingSetEditWizard) fWizard).setSelection(workingSet);
