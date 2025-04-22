    /**
     * Constructs a preference page of path variables.
     * Omits "Restore Defaults"/"Apply Changes" buttons.
     */
    public LinkedResourcesPreferencePage() {
        pathVariablesGroup = new PathVariablesGroup(true, IResource.FILE
                | IResource.FOLDER);

        this.noDefaultAndApplyButton();
    }

    /**
     * Resets this page's internal state and creates its UI contents.
     *
     * @see PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     */
    @Override
