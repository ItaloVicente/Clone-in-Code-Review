        checkboxViewer.addCheckStateListener(event -> checkboxViewer.setSelection(new StructuredSelection(event
		        .getElement())));
    }

    /**
     * Creates the widgets for the description.
     */
    private void createDescriptionArea(Composite mainComposite) {

        Font mainFont = mainComposite.getFont();
        Composite textComposite = new Composite(mainComposite, SWT.NONE);
        textComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout textLayout = new GridLayout();
        textLayout.marginWidth = 0;
        textLayout.marginHeight = 0;
        textComposite.setLayout(textLayout);
        textComposite.setFont(mainFont);

        Label descriptionLabel = new Label(textComposite, SWT.NONE);
        descriptionLabel.setText(WorkbenchMessages.DecoratorsPreferencePage_description);
        descriptionLabel.setFont(mainFont);

        descriptionText = new Text(textComposite, SWT.MULTI | SWT.WRAP
                | SWT.READ_ONLY | SWT.BORDER | SWT.H_SCROLL);
        descriptionText.setLayoutData(new GridData(GridData.FILL_BOTH));
        descriptionText.setFont(mainFont);
    }

    /**
     * Populates the list of decorators.
     */
    private void populateDecorators() {
        DecoratorDefinition[] definitions = getAllDefinitions();
        checkboxViewer.setInput(definitions);
        for (DecoratorDefinition definition : definitions) {
            checkboxViewer.setChecked(definition, definition
                    .isEnabled());
        }
    }

    /**
     * Show the selected description in the text.
     */
    private void showDescription(DecoratorDefinition definition) {
        if (descriptionText == null || descriptionText.isDisposed()) {
            return;
        }
        String text = definition.getDescription();
        if (text == null || text.length() == 0) {
