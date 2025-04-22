            initializeDialogUnits(parent);

            Composite composite = new Composite(parent, SWT.NULL);
            composite.setLayout(new GridLayout());
            composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                    | GridData.HORIZONTAL_ALIGN_FILL));
            composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
            composite.setFont(parent.getFont());

            createRootDirectoryGroup(composite);

            createDestinationGroup(composite);

            createFileSelectionGroup(composite);
            createButtonsGroup(composite);
            createOptionsGroup(composite);

            restoreWidgetValues();
            updateWidgetEnablements();
            setPageComplete(determinePageCompletion());
            setErrorMessage(null);	// should not initially have error message

            setControl(composite);

            validateSourceGroup();
