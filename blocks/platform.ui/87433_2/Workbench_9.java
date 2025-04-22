				getThemeManager().addPropertyChangeListener(event -> {
					if (IThemeManager.CHANGE_CURRENT_THEME.equals(event.getProperty())) {
						Object oldValue = event.getOldValue();
						if (oldValue != null && oldValue instanceof ITheme) {
							((ITheme) oldValue).removePropertyChangeListener(themeToPreferencesFontSynchronizer);
						}
						Object newValue = event.getNewValue();
						if (newValue != null && newValue instanceof ITheme) {
							((ITheme) newValue).addPropertyChangeListener(themeToPreferencesFontSynchronizer);
