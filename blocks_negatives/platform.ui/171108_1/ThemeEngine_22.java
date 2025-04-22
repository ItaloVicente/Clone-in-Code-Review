			boolean flag = true;
			if (prefThemeId != null) {
				for (ITheme t : getThemes()) {
					if (prefThemeId.equals(t.getId())) {
						setTheme(t, false);
						flag = false;
						break;
					}
