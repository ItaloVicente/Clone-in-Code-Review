    /**
     * Add external editors to the editor mapping.
     */
    private void addExternalEditorsToEditorMap() {
	for (FileEditorMapping map : typeEditorMappings.allMappings()) {
            IEditorDescriptor[] descArray = map.getEditors();
            for (IEditorDescriptor desc : descArray) {
