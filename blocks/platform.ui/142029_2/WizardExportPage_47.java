		}
	}

	protected void restoreResourceSpecificationWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String pageName = getName();
			boolean exportAllResources = settings.getBoolean(STORE_EXPORT_ALL_RESOURCES_ID + pageName);

			if (!exportAllResourcesPreSet) {
				exportAllTypesRadio.setSelection(exportAllResources);
				exportSpecifiedTypesRadio.setSelection(!exportAllResources);
			}

			if (initialTypesFieldValue == null) {
				String[] selectedTypes = settings.getArray(STORE_SELECTED_TYPES_ID + pageName);
				if (selectedTypes != null) {
					if (selectedTypes.length > 0) {
