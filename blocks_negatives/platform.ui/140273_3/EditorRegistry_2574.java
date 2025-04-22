    }

    /**
     * Initialize the registry state from plugin declarations and preference
     * overrides.
     */
    private void initializeFromStorage() {
        typeEditorMappings = new EditorMap();
        extensionImages = new HashMap<>();

        EditorRegistryReader registryReader = new EditorRegistryReader();
        registryReader.addEditors(this);
        sortInternalEditors();
        rebuildInternalEditorMap();

        IPreferenceStore store = PlatformUI.getPreferenceStore();
        String defaultEditors = store
                .getString(IPreferenceConstants.DEFAULT_EDITORS);
        String chachedDefaultEditors = store
                .getString(IPreferenceConstants.DEFAULT_EDITORS_CACHE);

        if (defaultEditors == null
                || defaultEditors.equals(chachedDefaultEditors)) {
            setProductDefaults(defaultEditors);
        } else {
            setProductDefaults(defaultEditors);
            store.putValue(IPreferenceConstants.DEFAULT_EDITORS_CACHE,
                    defaultEditors);
        }
        addExternalEditorsToEditorMap();
    }

    /**
     * Set the default editors according to the preference store which can be
     * overwritten in the file properties.ini.  In the form:
     * <p>
     * <code>ext1:id1;ext2:id2;...</code>
     * </p>
     *
     * @param defaultEditors the default editors to set
     */
    private void setProductDefaults(String defaultEditors) {
        if (defaultEditors == null || defaultEditors.length() == 0) {
