
	private void refreshAllLabels() {
		tree.getViewer().refresh(); // refresh all the labels in the tree
	}

	private boolean isAvailableInCurrentTheme(ThemeElementDefinition definition) {
		if (definition instanceof FontDefinition) {
			FontData[] value = ((FontDefinition) definition).getValue();
			return value != null && value != EMPRY_FONT_DATA_VALUE
					&& fontRegistry.get(definition.getId()) != null;
		}
		RGB value = ((ColorDefinition) definition).getValue();
		return value != null && value != EMPTY_COLOR_VALUE
				&& colorRegistry.get(definition.getId()) != null;
	}

	private String fomatDescription(ThemeElementDefinition definition) {
		String description = definition.getDescription() != null ? definition.getDescription() : ""; //$NON-NLS-1$
		if (isAvailableInCurrentTheme(definition)) {
			return description;
		}
		return MessageFormat.format(RESOURCE_BUNDLE.getString("definitionNotAvailInTheme"), //$NON-NLS-1$
				new Object[] { description }).trim();
	}
