	public List<String> getStylesheets(ITheme selection) {
		List<String> ss  = stylesheets.get(selection.getId());
		return ss == null ? new ArrayList<>() : ss;
	}

	public void themeModified(ITheme theme, List<String> paths) {
		modifiedStylesheets.put(theme.getId(), paths);
		setTheme(theme, false, true);
	}
