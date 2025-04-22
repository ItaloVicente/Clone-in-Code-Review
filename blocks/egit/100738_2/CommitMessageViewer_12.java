
	@Override
	protected void handleJFacePreferencesChange(PropertyChangeEvent event) {
		if (JFaceResources.TEXT_FONT.equals(event.getProperty())) {
			Font themeFont = UIUtils
					.getFont(UIPreferences.THEME_CommitMessageFont);
			Font jFaceFont = JFaceResources.getTextFont();
			if (themeFont.equals(jFaceFont)) {
				setFont(jFaceFont);
			}
		} else {
			super.handleJFacePreferencesChange(event);
		}
	}

