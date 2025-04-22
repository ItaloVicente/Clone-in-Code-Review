    /**
     * Create the widget for the user dialog preference.
     *
     * @param composite
     */
    protected void createShowUserDialogPref(Composite composite) {
        showUserDialogButton = new Button(composite, SWT.CHECK);
        showUserDialogButton.setText(WorkbenchMessages.WorkbenchPreference_RunInBackgroundButton);
        showUserDialogButton.setToolTipText(WorkbenchMessages.WorkbenchPreference_RunInBackgroundToolTip);
        showUserDialogButton.setSelection(WorkbenchPlugin.getDefault()
                .getPreferenceStore().getBoolean(
                        IPreferenceConstants.RUN_IN_BACKGROUND));
    }

