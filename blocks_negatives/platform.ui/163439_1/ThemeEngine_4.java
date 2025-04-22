							+ "' is already registered");
				}
			}
			Theme theme = new Theme(id, label);
			if (osVersion != "") {
				theme.setOsVersion(osVersion);
