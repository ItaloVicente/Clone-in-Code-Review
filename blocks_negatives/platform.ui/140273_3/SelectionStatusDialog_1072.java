        Font font = parent.getFont();
        Composite composite = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        if (!fStatusLineAboveButtons) {
            layout.numColumns = 2;
        }
        layout.marginHeight = 0;
        layout.marginLeft = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
        layout.marginWidth = 0;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        composite.setFont(font);

        if (!fStatusLineAboveButtons && isHelpAvailable()) {
        	createHelpControl(composite);
        }
        fStatusLine = new MessageLine(composite);
        fStatusLine.setAlignment(SWT.LEFT);
        GridData statusData = new GridData(GridData.FILL_HORIZONTAL);
        fStatusLine.setErrorStatus(null);
        fStatusLine.setFont(font);
        if (fStatusLineAboveButtons && isHelpAvailable()) {
        	statusData.horizontalSpan = 2;
        	createHelpControl(composite);
        }
        fStatusLine.setLayoutData(statusData);
