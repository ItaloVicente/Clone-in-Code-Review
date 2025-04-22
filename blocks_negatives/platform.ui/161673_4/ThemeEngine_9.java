		Theme theme = new Theme(id, label);
		if (osVersion != "") {
			theme.setOsVersion(osVersion);
		}
		themes.add(theme);
		registerStyle(id, basestylesheetURI);
		return theme;
	}
