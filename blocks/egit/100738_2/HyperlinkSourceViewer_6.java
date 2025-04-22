	private void setColors() {
		IPreferenceStore store = EditorsUI.getPreferenceStore();
		StyledText styledText = getTextWidget();
		setColor(styledText, store,
				AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND,
				store.getBoolean(
						AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT));
		setColor(styledText, store,
				AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND,
				store.getBoolean(
						AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT));
		setColor(styledText, store,
				AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND,
				store.getBoolean(
						AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND_SYSTEM_DEFAULT));
		setColor(styledText, store,
				AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND,
				store.getBoolean(
						AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND_SYSTEM_DEFAULT));
	}

	private void setColor(StyledText styledText, IPreferenceStore store,
			String key, boolean useDefault) {
		Color newColor = useDefault ? null
				: createColor(styledText.getDisplay(), store, key);
		switch (key) {
		case AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND:
			styledText.setForeground(newColor);
			break;
		case AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND:
			styledText.setBackground(newColor);
			break;
		case AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND:
			styledText.setSelectionForeground(newColor);
			break;
		case AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND:
			styledText.setSelectionBackground(newColor);
			break;
		default:
			return;
		}
		Color oldColor = customColors.remove(key);
		if (oldColor != null) {
			oldColor.dispose();
		}
		customColors.put(key, newColor);
	}

	private Color createColor(Display display, IPreferenceStore store,
			String key) {
		RGB rgb = null;
		if (store.contains(key)) {
			if (store.isDefault(key)) {
				rgb = PreferenceConverter.getDefaultColor(store, key);
			} else {
				rgb = PreferenceConverter.getColor(store, key);
			}
			if (rgb != null) {
				return new Color(display, rgb);
			}
		}
		return null;
	}

