        openAfterDelayButton.setLayoutData(data);

        createNoteComposite(font, buttonComposite, WorkbenchMessages.Preference_note,
                WorkbenchMessages.WorkbenchPreference_noEffectOnAllViews);
    }

    private void selectClickMode(boolean singleClick) {
        openOnSingleClick = singleClick;
        selectOnHoverButton.setEnabled(openOnSingleClick);
        openAfterDelayButton.setEnabled(openOnSingleClick);
    }

    /**
     * Utility method that creates a radio button instance and sets the default
     * layout data.
     *
     * @param parent
     *            the parent for the new button
     * @param label
     *            the label for the new button
     * @return the newly-created button
     */
    protected static Button createRadioButton(Composite parent, String label) {
        Button button = new Button(parent, SWT.RADIO | SWT.LEFT);
        button.setText(label);
        return button;
    }

    /**
     * Utility method that creates a combo box
     *
     * @param parent
     *            the parent for the new label
     * @return the new widget
     */
    protected static Combo createCombo(Composite parent) {
        Combo combo = new Combo(parent, SWT.READ_ONLY);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
        combo.setLayoutData(data);
        return combo;
    }

    /**
     * Utility method that creates a label instance and sets the default layout
     * data.
     *
     * @param parent
     *            the parent for the new label
     * @param text
     *            the text for the new label
     * @return the new label
     */
    protected static Label createLabel(Composite parent, String text) {
        Label label = new Label(parent, SWT.LEFT);
        label.setText(text);
        GridData data = new GridData();
        data.horizontalSpan = 1;
        data.horizontalAlignment = GridData.FILL;
        label.setLayoutData(data);
        return label;
    }

    /**
     * Creates a tab of one horizontal spans.
     *
     * @param parent
     *            the parent in which the tab should be created
     */
    protected static void createSpace(Composite parent) {
        Label vfiller = new Label(parent, SWT.LEFT);
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.BEGINNING;
        gridData.grabExcessHorizontalSpace = false;
        gridData.verticalAlignment = GridData.CENTER;
        gridData.grabExcessVerticalSpace = false;
        vfiller.setLayoutData(gridData);
    }

    /**
     * Returns preference store that belongs to the our plugin.
     *
     * @return the preference store for this plugin
     */
    @Override
