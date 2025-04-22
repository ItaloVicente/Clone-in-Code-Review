
	private void refreshAllLabels() {
		updateThemeInfo(PlatformUI.getWorkbench().getThemeManager());
		tree.getViewer().refresh(); // refresh all the labels in the tree
	}

	private boolean isAvailableInCurrentTheme(ThemeElementDefinition definition) {
		if (definition instanceof FontDefinition) {
			return fontRegistry.get(definition.getId()) != null;
		}
		return colorRegistry.get(definition.getId()) != null;
	}

	private String fomatDescription(ThemeElementDefinition definition, Resource resource) {
		String description = definition.getDescription() != null ? definition.getDescription() : ""; //$NON-NLS-1$
		if (resource != null) {
			return description;
		}
		return MessageFormat.format(RESOURCE_BUNDLE.getString("definitionNotAvailInTheme"), //$NON-NLS-1$
				new Object[] { description }).trim();
	}
