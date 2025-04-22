		checkboxViewer.addCheckStateListener(
				event -> checkboxViewer.setSelection(new StructuredSelection(event.getElement())));
	}

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

		descriptionText = new Text(textComposite, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY | SWT.BORDER | SWT.H_SCROLL);
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.minimumHeight = 50;
		descriptionText.setLayoutData(layoutData);
		descriptionText.setFont(mainFont);
	}

	private void populateDecorators() {
		DecoratorDefinition[] definitions = getAllDefinitions();
		checkboxViewer.setInput(definitions);
		for (DecoratorDefinition definition : definitions) {
			checkboxViewer.setChecked(definition, definition.isEnabled());
		}
	}

	private void showDescription(DecoratorDefinition definition) {
		if (descriptionText == null || descriptionText.isDisposed()) {
			return;
		}
		String text = definition.getDescription();
		if (text == null || text.length() == 0) {
