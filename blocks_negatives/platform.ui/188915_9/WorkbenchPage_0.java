		initMaxFileSize();
	}

	private void initMaxFileSize() {
		IPreferenceStore preferenceStore = PrefUtil.getInternalPreferenceStore();
		maxFileSize = preferenceStore.getLong(IPreferenceConstants.LARGE_DOC_SIZE_FOR_EDITORS);
		checkDocumentSize = maxFileSize != 0;
