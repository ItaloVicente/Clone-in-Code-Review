        return type.toLowerCase();
    }

    /**
     * Return a key that combines the file's name and extension of the given
     * mapping
     *
     * @param mapping the mapping to generate a key for
     */
    private String mappingKeyFor(FileEditorMapping mapping) {
        return mappingKeyFor(mapping.getName()
    }

    /**
     * Rebuild the editor map
     */
    private void rebuildEditorMap() {
        rebuildInternalEditorMap();
        addExternalEditorsToEditorMap();
    }

    /**
     * Rebuild the internal editor mapping.
     */
    private void rebuildInternalEditorMap() {
        mapIDtoEditor = initialIdToEditorMap(mapIDtoEditor.size());

        for (IEditorDescriptor desc : sortedEditorsFromPlugins) {
            mapIDtoEditor.put(desc.getId(), desc);
        }
    }

    @Override
