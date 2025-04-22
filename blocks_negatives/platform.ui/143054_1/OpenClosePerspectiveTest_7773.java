        final IPerspectiveRegistry registry = WorkbenchPlugin.getDefault()
                .getPerspectiveRegistry();
        final IPerspectiveDescriptor perspective1 = registry
                .findPerspectiveWithId(id);

        if (perspective1 == null) {
            System.out.println("Unknown perspective id: " + id);
            return;
        }

        IWorkbenchWindow window = openTestWindow();
        final IWorkbenchPage activePage = window.getActivePage();

        activePage.setPerspective(perspective1);
        IViewReference [] refs = activePage.getViewReferences();
        String [] ids = new String[refs.length];
        for (int i = 0; i < refs.length; i++) {
            ids[i] = refs[i].getId();
        }
        closePerspective(activePage);
        for (int i = 0; i < ids.length; i++) {
            activePage.showView(ids[i]);
        }

        tagIfNecessary("UI - Open/Close " + perspective1.getLabel() + " Perspective", Dimension.ELAPSED_PROCESS);

        exercise(new TestRunnable() {
            @Override
