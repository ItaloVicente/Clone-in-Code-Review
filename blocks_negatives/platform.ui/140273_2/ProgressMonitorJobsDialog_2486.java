        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.makeColumnsEqualWidth = false;
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
        layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
        composite.setLayout(layout);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.horizontalSpan = 2;
        data.horizontalAlignment = GridData.END;
        data.grabExcessHorizontalSpace = true;
        composite.setLayoutData(data);
        composite.setFont(parent.getFont());
        if (arrowCursor == null) {
