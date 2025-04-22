        autoSaveAllButton = new Button(composite, SWT.CHECK);
        autoSaveAllButton.setText(IDEWorkbenchMessages.IDEWorkspacePreference_savePriorToBuilding);
        autoSaveAllButton.setToolTipText(IDEWorkbenchMessages.IDEWorkspacePreference_savePriorToBuildingToolTip);
        autoSaveAllButton.setSelection(getIDEPreferenceStore().getBoolean(
                IDEInternalPreferences.SAVE_ALL_BEFORE_BUILD));
    }

    protected void createAutoBuildPref(Composite composite) {
        autoBuildButton = new Button(composite, SWT.CHECK);
        autoBuildButton.setText(IDEWorkbenchMessages.IDEWorkspacePreference_autobuild);
        autoBuildButton.setToolTipText(IDEWorkbenchMessages.IDEWorkspacePreference_autobuildToolTip);
        autoBuildButton.setSelection(ResourcesPlugin.getWorkspace()
                .isAutoBuilding());
    }
