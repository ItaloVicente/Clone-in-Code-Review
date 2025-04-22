		if (themeListener != null) {
			PlatformUI.getWorkbench().getThemeManager()
					.removePropertyChangeListener(themeListener);
		}
		if (uiPrefsListener != null) {
			getPreferenceStore().removePropertyChangeListener(uiPrefsListener);
		}
