        Composite composite = createComposite(parent);

        createOpenPerspButtonGroup(composite);
        createProjectPerspectiveGroup(composite);
        createCustomizePerspective(composite);

        return composite;
    }

    /**
     * Creates a composite that contains buttons for selecting the preference
     * opening new project selections.
     */
    private void createProjectPerspectiveGroup(Composite composite) {

        Composite projectComposite = new Composite(composite, SWT.NONE);
        projectComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        projectComposite.setFont(composite.getFont());

        String[][] namesAndValues = {
                { PSPM_ALWAYS_TEXT, IDEInternalPreferences.PSPM_ALWAYS },
                { PSPM_NEVER_TEXT, IDEInternalPreferences.PSPM_NEVER },
                { PSPM_PROMPT_TEXT, IDEInternalPreferences.PSPM_PROMPT } };
        projectSwitchField = new RadioGroupFieldEditor(
                IDEInternalPreferences.PROJECT_SWITCH_PERSP_MODE,
                PROJECT_SWITCH_PERSP_MODE_TITLE, namesAndValues.length,
                namesAndValues, projectComposite, true);
        projectSwitchField.setPreferenceStore(getIDEPreferenceStore());
        projectSwitchField.setPage(this);
        projectSwitchField.load();
    }

    /**
     * Returns the IDE preference store.
     */
    protected IPreferenceStore getIDEPreferenceStore() {
        return IDEWorkbenchPlugin.getDefault().getPreferenceStore();
    }

    @Override
