		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String pageName = getName();

			String[] selectedTypesNames = settings.getArray(STORE_SELECTED_TYPES_ID + pageName);
			if (selectedTypesNames == null) {
