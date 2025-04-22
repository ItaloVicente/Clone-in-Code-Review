
		boolean hasDarkTheme = false;
		for (ITheme t : getThemes()) {
			if (t.getId().startsWith(E4_DARK_THEME_ID)) { // theme id can end with OS version numbers
				hasDarkTheme = true;
				break;
			}
		}

