    }

    /**
     * Return whether the user is allowed to enter a new container name or just
     * choose from existing ones.
     * <p>
     * Subclasses must implement this method.
     * </p>
     *
     * @return <code>true</code> if new ones are okay, and <code>false</code>
     *  if only existing ones are allowed
     */
    protected abstract boolean allowNewContainerName();

    /**
     * Creates a new label with a bold font.
     *
     * @param parent the parent control
     * @param text the label text
     * @return the new label control
     */
    protected Label createBoldLabel(Composite parent, String text) {
        Label label = new Label(parent, SWT.NONE);
        label.setFont(JFaceResources.getBannerFont());
        label.setText(text);
        GridData data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        label.setLayoutData(data);
        return label;
    }

    /**
     * Creates the import/export options group controls.
     * <p>
     * The <code>WizardDataTransferPage</code> implementation of this method does
     * nothing. Subclasses wishing to define such components should reimplement
     * this hook method.
     * </p>
     *
     * @param optionsGroup the parent control
     */
    protected void createOptionsGroupButtons(Group optionsGroup) {
    }

    /**
     * Creates a new label with a bold font.
     *
     * @param parent the parent control
     * @param text the label text
     * @return the new label control
     */
    protected Label createPlainLabel(Composite parent, String text) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(text);
        label.setFont(parent.getFont());
        GridData data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        label.setLayoutData(data);
        return label;
    }

    /**
     * Creates a horizontal spacer line that fills the width of its container.
     *
     * @param parent the parent control
     */
    protected void createSpacer(Composite parent) {
        Label spacer = new Label(parent, SWT.NONE);
        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.BEGINNING;
        spacer.setLayoutData(data);
    }

    /**
     * Returns whether this page is complete. This determination is made based upon
     * the current contents of this page's controls.  Subclasses wishing to include
     * their controls in this determination should override the hook methods
     * <code>validateSourceGroup</code> and/or <code>validateOptionsGroup</code>.
     *
     * @return <code>true</code> if this page is complete, and <code>false</code> if
     *   incomplete
     * @see #validateSourceGroup
     * @see #validateOptionsGroup
     */
    protected boolean determinePageCompletion() {
        boolean complete = validateSourceGroup() && validateDestinationGroup()
                && validateOptionsGroup();

        if (complete) {
