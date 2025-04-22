        super.doFillIntoGrid(parent, numColumns - 1);
        changeButton = getChangeControl(parent);
        GridData gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        int widthHint = convertHorizontalDLUsToPixels(changeButton,
                IDialogConstants.BUTTON_WIDTH);
        gd.widthHint = Math.max(widthHint, changeButton.computeSize(
                SWT.DEFAULT, SWT.DEFAULT, true).x);
        changeButton.setLayoutData(gd);
    }

    /**
     * Get the change control. Create it in parent if required.
     * @param parent
     * @return Button
     */
    protected Button getChangeControl(Composite parent) {
        if (changeButton == null) {
            changeButton = new Button(parent, SWT.PUSH);
            if (changeButtonText == null) {
