        s.put(COLUMNS, array);
    }

    /**
     * Return a dialog setting section for this dialog
     */
    private IDialogSettings getDialogSettings() {
        IDialogSettings settings = WorkbenchPlugin.getDefault()
                .getDialogSettings();
        IDialogSettings thisSettings = settings
                .getSection(getClass().getName());
        if (thisSettings == null) {
