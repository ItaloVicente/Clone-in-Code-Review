        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL));
        composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        createSourceGroup(composite);

        createSpacer(composite);

        createBoldLabel(composite, IDEWorkbenchMessages.WizardImportPage_destinationLabel);
        createDestinationGroup(composite);

        createSpacer(composite);

        createBoldLabel(composite, IDEWorkbenchMessages.WizardImportPage_options);
        createOptionsGroup(composite);

        restoreWidgetValues();
        updateWidgetEnablements();
        setPageComplete(determinePageCompletion());

        setControl(composite);
    }

    /**
     * Creates the import destination specification controls.
     *
     * @param parent the parent control
     */
    protected final void createDestinationGroup(Composite parent) {
        Composite containerGroup = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        containerGroup.setLayout(layout);
        containerGroup.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));

        Label resourcesLabel = new Label(containerGroup, SWT.NONE);
        resourcesLabel.setText(IDEWorkbenchMessages.WizardImportPage_folder);

        containerNameField = new Text(containerGroup, SWT.SINGLE | SWT.BORDER);
        containerNameField.addListener(SWT.Modify, this);
        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
                | GridData.GRAB_HORIZONTAL);
        data.widthHint = SIZING_TEXT_FIELD_WIDTH;
        containerNameField.setLayoutData(data);

        containerBrowseButton = new Button(containerGroup, SWT.PUSH);
        containerBrowseButton.setText(IDEWorkbenchMessages.WizardImportPage_browseLabel);
        containerBrowseButton.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_FILL));
        containerBrowseButton.addListener(SWT.Selection, this);

        initialPopulateContainerField();
    }

    /**
     * Creates the import source specification controls.
     * <p>
     * Subclasses must implement this method.
     * </p>
     *
     * @param parent the parent control
     */
    protected abstract void createSourceGroup(Composite parent);

    /**
     * Display an error dialog with the specified message.
     *
     * @param message the error message
     */
    @Override
