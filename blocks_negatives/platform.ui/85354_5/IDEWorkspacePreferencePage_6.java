    }

    /**
     * Create a composite that contains entry fields specifying the workspace name
     * preference.
     *
     * @param composite the Composite the group is created in.
     */
    private void createWindowTitleGroup(Composite composite) {
        Composite groupComposite = new Composite(composite, SWT.LEFT);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        groupComposite.setLayout(layout);
        GridData gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        groupComposite.setLayoutData(gd);

        workspaceName = new StringFieldEditor(
                IDEInternalPreferences.WORKSPACE_NAME, IDEWorkbenchMessages.IDEWorkspacePreference_workspaceName,
                groupComposite);

        workspaceName.setPreferenceStore(getIDEPreferenceStore());
        workspaceName.load();
        workspaceName.setPage(this);
    }
