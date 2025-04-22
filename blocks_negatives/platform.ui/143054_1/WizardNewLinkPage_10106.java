                handleLinkTargetBrowseButtonPressed();
            }
        });
        browseButton.setEnabled(enabled);

        fill = new Label(locationGroup, SWT.NONE);
        data = new GridData();
        data.horizontalSpan = 2;
        fill.setLayoutData(data);

        variablesButton = new Button(locationGroup, SWT.PUSH);
        setButtonLayoutData(variablesButton);
        variablesButton.setFont(font);
        variablesButton.setText(IDEWorkbenchMessages.WizardNewLinkPage_variablesButton);
        variablesButton.addSelectionListener(new SelectionAdapter() {
            @Override
