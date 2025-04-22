    /**
     * Visually checks the previously-specified elements in this dialog's list
     * viewer.
     */
    private void checkInitialSelections() {
        IFileEditorMapping editorMappings[] = ((EditorRegistry) PlatformUI
				.getWorkbench().getEditorRegistry()).getUnifiedMappings();
        ArrayList selectedMappings = new ArrayList();
        for (IFileEditorMapping mapping : editorMappings) {
            if (this.initialSelections.contains(mapping.getExtension())) {
                listViewer.setChecked(mapping, true);
                selectedMappings.add(mapping.getExtension());
            } else {
                if (this.initialSelections.contains(mapping.getLabel())) {
                    listViewer.setChecked(mapping, true);
                    selectedMappings.add(mapping.getLabel());
                }
            }
        }
        Iterator initialIterator = this.initialSelections.iterator();
        StringBuilder entries = new StringBuilder();
        while (initialIterator.hasNext()) {
            String nextExtension = (String) initialIterator.next();
            if (!selectedMappings.contains(nextExtension)) {
            	if (entries.length() != 0) {
