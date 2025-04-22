        super.doSetUp();
        window = openTestWindow();
        page = window.getActivePage();
        view = (RawIViewPart) page
                .showView("org.eclipse.ui.tests.workbenchpart.RawIViewPart");
        ref = page
                .findViewReference("org.eclipse.ui.tests.workbenchpart.RawIViewPart");
        ref.addPropertyListener(propertyListener);
        titleChangeEvent = false;
        nameChangeEvent = false;
        contentChangeEvent = false;
    }

    @Override
