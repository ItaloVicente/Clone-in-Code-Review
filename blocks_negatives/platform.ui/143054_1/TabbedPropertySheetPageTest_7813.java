        /**
         * Select the Tests view.
         */
        IViewPart view = workbenchPage.showView(TestsView.TESTS_VIEW_ID);
        assertNotNull(view);
        assertTrue(view instanceof TestsView);
        testsView = (TestsView) view;
