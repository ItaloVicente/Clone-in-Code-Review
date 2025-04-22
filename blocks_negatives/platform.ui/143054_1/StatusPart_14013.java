        detailsButton = new Button(buttonParent, SWT.PUSH);
        detailsButton.addSelectionListener(widgetSelectedAdapter(e -> showDetails(!showingDetails)));

        detailsButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, false));
        detailsButton.setVisible(reason.getException() != null);

        createShowLogButton(buttonParent);

        updateDetailsText();

        detailsArea = new Composite(parent, SWT.NONE);
        detailsArea.setBackground(bgColor);
        detailsArea.setForeground(fgColor);
        GridData data = new GridData(GridData.FILL_BOTH);
        data.horizontalSpan = 3;
        data.verticalSpan = 1;
        detailsArea.setLayoutData(data);
        detailsArea.setLayout(new FillLayout());
        parent.layout(true);
    }

    /**
     * Return the image for the upper-left corner of this part
     *
     * @return the image
     */
    private Image getImage() {
        Display d = Display.getCurrent();

        switch(reason.getSeverity()) {
        case IStatus.ERROR:
            return d.getSystemImage(SWT.ICON_ERROR);
        case IStatus.WARNING:
            return d.getSystemImage(SWT.ICON_WARNING);
        default:
            return d.getSystemImage(SWT.ICON_INFORMATION);
        }
    }

    private void showDetails(boolean shouldShow) {
        if (shouldShow == showingDetails) {
            return;
        }
        this.showingDetails = shouldShow;
        updateDetailsText();
    }

    private void updateDetailsText() {
        if (details != null) {
            details.dispose();
            details = null;
        }

        if (showingDetails) {
            detailsButton.setText(IDialogConstants.HIDE_DETAILS_LABEL);
            Text detailsText = new Text(detailsArea, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL
                    | SWT.MULTI | SWT.READ_ONLY | SWT.LEFT_TO_RIGHT);
            detailsText.setText(getDetails(reason));
