        if (buttonId == IDialogConstants.DETAILS_ID) {
            detailsPressed();
            return;
        }
        super.buttonPressed(buttonId);
    }

    /**
     * Handles selection of the Details button.
     */
    private void detailsPressed() {
        showDetails = !showDetails;
        setDetailButtonLabel();
        setDetailHints();
        setDetails();
        ((Composite) getDialogArea()).layout(true);
        getShell().setSize(getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }
