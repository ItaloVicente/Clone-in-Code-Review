		}
	}

	void restoreLocation() {
		if (editorInfo.editorInput != null && editorInfo.editorID != null) {
			try {
				IEditorPart editor = page.openEditor(editorInfo.editorInput, editorInfo.editorID, true);
				if (location == null) {
					if (editor instanceof INavigationLocationProvider) {
						location = ((INavigationLocationProvider) editor).createEmptyNavigationLocation();
