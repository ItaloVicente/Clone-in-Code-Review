	private ColorDescriptor createEditorColorDescriptor(String key) {
		return ColorDescriptor.createFrom(PreferenceConverter.getColor(
				EditorsUI.getPreferenceStore(), key));
	}

	private Color getEditorColor(String key) {
		return (Color) colors.get(createEditorColorDescriptor(key));
	}

	private void styleViewer() {
		IPreferenceStore store = EditorsUI.getPreferenceStore();
		Color foreground = null;
		if (!store
				.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT))
			foreground = getEditorColor(AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND);

		Color background = null;
		if (!store
				.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT))
			background = getEditorColor(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND);

		Color selectionForeground = null;
		if (!store
				.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND_SYSTEM_DEFAULT))
			selectionForeground = getEditorColor(AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND);

		Color selectionBackground = null;
		if (!store
				.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND_SYSTEM_DEFAULT))
			selectionBackground = getEditorColor(AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND);

		StyledText text = getTextWidget();
		text.setForeground(foreground);
		text.setBackground(background);
		text.setSelectionForeground(selectionForeground);
		text.setSelectionBackground(selectionBackground);
		text.setFont(JFaceResources.getFont(JFaceResources.TEXT_FONT));
