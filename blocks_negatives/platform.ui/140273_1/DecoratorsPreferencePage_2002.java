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
