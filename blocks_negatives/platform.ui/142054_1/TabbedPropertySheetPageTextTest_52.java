        /**
         * Close the existing perspectives.
         */
        IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow();
        assertNotNull(workbenchWindow);
        IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
        assertNotNull(workbenchPage);
        workbenchPage.closeAllPerspectives(false, false);

        /**
         * Open the tests perspective.
         */
        PlatformUI.getWorkbench().showPerspective(
            TestsPerspective.TESTS_PERSPECTIVE_ID, workbenchWindow);
