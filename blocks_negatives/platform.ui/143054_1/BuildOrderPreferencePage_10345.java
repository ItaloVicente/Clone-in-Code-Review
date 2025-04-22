        if (useDefault) {
            this.buildList.setItems(getDefaultProjectOrder());
        } else {
            this.buildList.setItems(buildOrder);
        }

        return composite;

    }

    /**
     * Adds in a spacer.
     *
     * @param composite the parent composite
     */
    private void createSpacer(Composite composite) {
        Label spacer = new Label(composite, SWT.NONE);
        GridData spacerData = new GridData();
        spacerData.horizontalSpan = 2;
        spacer.setLayoutData(spacerData);
    }

    /**
     * Create the default path button. Set it to selected based on the current workspace
     * build path.
     * @param composite org.eclipse.swt.widgets.Composite
     * @param selected - the boolean that indicates the buttons initial state
     */
    private void createDefaultPathButton(Composite composite, boolean selected) {

        defaultOrderInitiallySelected = selected;

        this.defaultOrderButton = new Button(composite, SWT.LEFT | SWT.CHECK);
        this.defaultOrderButton.setSelection(selected);
        this.defaultOrderButton.setText(DEFAULTS_LABEL);
        SelectionListener listener = new SelectionAdapter() {
            @Override
