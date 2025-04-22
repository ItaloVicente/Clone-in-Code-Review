        super.doSetUp();
        window = openTestWindow();
        page = window.getActivePage();
        view = (OverriddenTitleView) page
                .showView("org.eclipse.ui.tests.workbenchpart.OverriddenTitleView");
        view.addPropertyListener(propertyListener);
        titleChangeEvent = false;
        nameChangeEvent = false;
        contentChangeEvent = false;
    }

    @Override
