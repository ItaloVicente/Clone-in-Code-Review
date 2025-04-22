			String os = Platform.getOS();
			String themeToRestore = (Platform.OS_LINUX.equals(os) || Platform.OS_MACOSX.equals(os))
					&& Display.isSystemDarkTheme() && hasDarkTheme ? E4_DARK_THEME_ID : alternateTheme;
			if (themeToRestore != null && flag) {
				setTheme(themeToRestore, false);
			}
