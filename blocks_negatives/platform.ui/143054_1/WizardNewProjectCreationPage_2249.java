        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        createProjectNameGroup(composite);
        locationArea = new ProjectContentsLocationArea(getErrorReporter(), composite);
        if(initialProjectFieldValue != null) {
