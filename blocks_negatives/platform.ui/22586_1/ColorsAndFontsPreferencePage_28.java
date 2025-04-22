
	private void refreshAllLabels() {
		updateThemeInfo(PlatformUI.getWorkbench().getThemeManager());
	}

	private boolean isAvailableInCurrentTheme(ThemeElementDefinition definition) {
		if (definition instanceof FontDefinition) {
			return fontRegistry.get(definition.getId()) != null;
		}
		return colorRegistry.get(definition.getId()) != null;
	}

	private String fomatDescription(ThemeElementDefinition definition, Resource resource) {
		if (resource != null) {
			return description;
		}
		return MessageFormat.format(RESOURCE_BUNDLE.getString("definitionNotAvailInTheme"), //$NON-NLS-1$
				new Object[] { description }).trim();
	}
