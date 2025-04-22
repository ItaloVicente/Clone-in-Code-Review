	}

	private void initializeFromStorage() {
		typeEditorMappings = new EditorMap();
		extensionImages = new HashMap<>();

		EditorRegistryReader registryReader = new EditorRegistryReader();
		registryReader.addEditors(this);
		sortInternalEditors();
		rebuildInternalEditorMap();

		IPreferenceStore store = PlatformUI.getPreferenceStore();
		String defaultEditors = store.getString(IPreferenceConstants.DEFAULT_EDITORS);
		String chachedDefaultEditors = store.getString(IPreferenceConstants.DEFAULT_EDITORS_CACHE);

		if (defaultEditors == null || defaultEditors.equals(chachedDefaultEditors)) {
			setProductDefaults(defaultEditors);
			loadAssociations(); // get saved earlier state
		} else {
			loadAssociations(); // get saved earlier state
			setProductDefaults(defaultEditors);
			store.putValue(IPreferenceConstants.DEFAULT_EDITORS_CACHE, defaultEditors);
		}
		addExternalEditorsToEditorMap();
	}

	private void setProductDefaults(String defaultEditors) {
		if (defaultEditors == null || defaultEditors.length() == 0) {
