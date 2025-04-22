	private void migrateBoundsSetting() {
		IDialogSettings settings = getDialogSettings();
		if (settings == null)
			return;

		final String className = getClass().getName();

		String key = className + DIALOG_USE_PERSISTED_BOUNDS;
		String value = settings.get(key);
		if (value == null || DIALOG_VALUE_MIGRATED_TO_34.equals(value))
			return;

		boolean storeBounds = settings.getBoolean(key);
		settings.put(className + DIALOG_USE_PERSISTED_LOCATION, storeBounds);
		settings.put(className + DIALOG_USE_PERSISTED_SIZE, storeBounds);
		settings.put(key, DIALOG_VALUE_MIGRATED_TO_34);
	}

