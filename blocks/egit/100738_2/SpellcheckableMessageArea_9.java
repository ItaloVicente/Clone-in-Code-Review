		final IPropertyChangeListener themeListener = event -> {
			String property = event.getProperty();
			if (IThemeManager.CHANGE_CURRENT_THEME.equals(property)
					|| UIPreferences.THEME_CommitMessageEditorFont
							.equals(property)) {
				Font themeFont = UIUtils
						.getFont(UIPreferences.THEME_CommitMessageEditorFont);
				getDisplay().asyncExec(() -> {
					if (!isDisposed()) {
						sourceViewer.setFont(themeFont);
					}
				});
