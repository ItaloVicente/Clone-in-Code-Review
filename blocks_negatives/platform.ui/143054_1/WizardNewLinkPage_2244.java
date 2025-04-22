                createLink = createLinkButton.getSelection();
                browseButton.setEnabled(createLink);
                variablesButton.setEnabled(createLink);
                linkTargetField.setEnabled(createLink);
                setPageComplete(validatePage());
            }
        };
        createLinkButton.addSelectionListener(listener);

        createLinkLocationGroup(topLevel, createLink);
        validatePage();

        setErrorMessage(null);
        setMessage(null);
        setControl(topLevel);
    }

    /**
     * Creates the link target location widgets.
     *
     * @param locationGroup the parent composite
     * @param enabled sets the initial enabled state of the widgets
     */
    private void createLinkLocationGroup(Composite locationGroup,
            boolean enabled) {
        Font font = locationGroup.getFont();
        Label fill = new Label(locationGroup, SWT.NONE);
        GridData data = new GridData();
        Button button = new Button(locationGroup, SWT.CHECK);
        data.widthHint = button.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
        button.dispose();
        fill.setLayoutData(data);

        linkTargetField = new Text(locationGroup, SWT.BORDER);
        data = new GridData(GridData.FILL_HORIZONTAL);
        linkTargetField.setLayoutData(data);
        linkTargetField.setFont(font);
        linkTargetField.setEnabled(enabled);
        linkTargetField.addModifyListener(e -> setPageComplete(validatePage()));
        if (initialLinkTarget != null) {
            linkTargetField.setText(initialLinkTarget);
        }

        browseButton = new Button(locationGroup, SWT.PUSH);
        setButtonLayoutData(browseButton);
        browseButton.setFont(font);
        browseButton.setText(IDEWorkbenchMessages.WizardNewLinkPage_browseButton);
        browseButton.addSelectionListener(new SelectionAdapter() {
            @Override
