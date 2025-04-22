	}

	@Override
	protected void handleDispose() {
		if (hyperlinkChangeListener != null) {
			EditorsUI.getPreferenceStore()
					.removePropertyChangeListener(hyperlinkChangeListener);
			hyperlinkChangeListener = null;
		}
		if (editorPropertyChangeListener != null) {
			EditorsUI.getPreferenceStore()
					.removePropertyChangeListener(editorPropertyChangeListener);
			editorPropertyChangeListener = null;
		}
		if (jFacePropertyChangeListener != null) {
			JFacePreferences.getPreferenceStore()
					.removePropertyChangeListener(jFacePropertyChangeListener);
			jFacePropertyChangeListener = null;
		}
		for (Color color : customColors.values()) {
			if (color != null) {
				color.dispose();
