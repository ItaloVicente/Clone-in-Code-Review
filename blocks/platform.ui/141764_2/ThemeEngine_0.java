		String themeToRestore = Platform.OS_MACOSX.equals(Platform.getOS())
				&& (Display.getSystemTheme() == SWT.THEME_DARK)
						? E4_DARK_THEME_ID
						: alternateTheme;

		if (themeToRestore != null && flag) {
			setTheme(themeToRestore, false);
