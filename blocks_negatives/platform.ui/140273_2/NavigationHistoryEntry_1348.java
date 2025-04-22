        }
    }

    /**
     * Restores the state of the entry and the location if needed and then
     * restores the location.
     */
    void restoreLocation() {
        if (editorInfo.editorInput != null && editorInfo.editorID != null) {
            try {
                IEditorPart editor = page.openEditor(editorInfo.editorInput,
                        editorInfo.editorID, true);
                if (location == null) {
                    if (editor instanceof INavigationLocationProvider) {
						location = ((INavigationLocationProvider) editor)
                                .createEmptyNavigationLocation();
