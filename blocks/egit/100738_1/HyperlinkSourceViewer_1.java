		setColors();
		editorPropertyChangeListener = event -> handleEditorPreferencesChange(
				event);
		EditorsUI.getPreferenceStore()
				.addPropertyChangeListener(editorPropertyChangeListener);
		jFacePropertyChangeListener = event -> handleJFacePreferencesChange(
				event);
		JFacePreferences.getPreferenceStore()
				.addPropertyChangeListener(jFacePropertyChangeListener);
