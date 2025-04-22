        if (!readEditors(editorTable)) {
            return false;
        }
        return readResources(editorTable);
    }

    /**
     * Return a friendly version of the given key suitable for use in the editor
     * map.
     */
    private String mappingKeyFor(String type) {
