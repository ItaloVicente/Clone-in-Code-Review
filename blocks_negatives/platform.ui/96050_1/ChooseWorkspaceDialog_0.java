        Composite panel = new Composite(parent, SWT.NONE);
        panel.setFont(parent.getFont());

        GridLayout layout = new GridLayout(1, false);
        layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
        panel.setLayout(layout);

        GridData data = new GridData(GridData.FILL_BOTH);
        data.verticalAlignment = GridData.END;
        panel.setLayoutData(data);

        Button button = new Button(panel, SWT.CHECK);
