            /*
             * Open a window with the MockViewPart, and make sure it now
             * enabled.
             */
            final IWorkbenchPage page = openTestWindow().getActivePage();
            final IViewPart openedView = page
                    .showView("org.eclipse.ui.tests.api.MockViewPart");
            page.activate(openedView);
            while (fWorkbench.getDisplay().readAndDispatch()) {
