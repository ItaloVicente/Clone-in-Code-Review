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
