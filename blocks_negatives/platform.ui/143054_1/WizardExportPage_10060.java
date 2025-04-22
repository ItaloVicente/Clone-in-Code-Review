    }

    /**
     * Sets the value of this page's "Types to export" field based upon the
     * collection of extensions.
     *
     * @param typeStrings the collection of extensions to populate the "Types to
     *   export" field with (element type: <code>String</code>)
     */
    protected void setTypesToExport(List typeStrings) {
        StringBuilder result = new StringBuilder();
        Iterator typesEnum = typeStrings.iterator();

        while (typesEnum.hasNext()) {
            result.append(typesEnum.next());
            result.append(TYPE_DELIMITER);
        }

        typesToExportField.setText(result.toString());
    }

    /**
     * Populates the resource name field based upon the currently selected resources.
     */
    protected void setupBasedOnInitialSelections() {
        if (initialExportFieldValue != null) {
            IResource specifiedSourceResource = getSourceResource();
            if (specifiedSourceResource == null) {
