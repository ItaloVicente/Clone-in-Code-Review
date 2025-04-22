	}

	protected void setTypesToExport(List typeStrings) {
		StringBuilder result = new StringBuilder();
		Iterator typesEnum = typeStrings.iterator();

		while (typesEnum.hasNext()) {
			result.append(typesEnum.next());
			result.append(TYPE_DELIMITER);
			result.append(" ");//$NON-NLS-1$
		}

		typesToExportField.setText(result.toString());
	}

	protected void setupBasedOnInitialSelections() {
		if (initialExportFieldValue != null) {
			IResource specifiedSourceResource = getSourceResource();
			if (specifiedSourceResource == null) {
