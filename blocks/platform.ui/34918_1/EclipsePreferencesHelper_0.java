	public static void setPreviousThemeId(String themeId) {
		previousThemeId = themeId;
	}

	public static void setCurrentThemeId(String themeId) {
		currentThemeId = themeId;
	}

	public static boolean isThemeChanged() {
		return currentThemeId != null && !currentThemeId.equals(previousThemeId);
	}

