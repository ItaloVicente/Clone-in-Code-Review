	private String getPreferenceThemeId() {
		return getPreferences().get(THEMEID_KEY, null);
	}

	private IEclipsePreferences getPreferences() {
		return InstanceScope.INSTANCE.getNode(FrameworkUtil.getBundle(ThemeEngine.class).getSymbolicName());
	}

	@Override
	public void restore(String alternateTheme) {
		String prefThemeId = getPreferenceThemeId();
		boolean flag = true;
		if (prefThemeId != null) {
			for (ITheme t : getThemes()) {
				if (prefThemeId.equals(t.getId())) {
					setTheme(t, false);
					flag = false;
					break;
