        PreferenceDialogWrapper dialog = null;
        PreferenceManager manager = WorkbenchPlugin.getDefault()
                .getPreferenceManager();
        if (manager != null) {
            dialog = new PreferenceDialogWrapper(getShell(), manager);
            dialog.create();
