        for (IEditorDescriptor element : array) {
            sortedEditorsFromPlugins.add(element);
        }
    }

    /**
     * Map of FileEditorMapping (extension to FileEditorMapping) Uses two
     * java.util.HashMap: one keeps the default which are set by the plugins and
     * the other keeps the changes made by the user through the preference page.
     */
    private static class EditorMap {
