        super.create();
        pattern.setFocus();
        getButton(IDialogConstants.OK_ID).setEnabled(okEnabled);
    }

    /**
     * Creates the contents of this dialog, initializes the
     * listener and the update thread.
     *
     * @param parent parent to create the dialog widgets in
     */
    @Override
