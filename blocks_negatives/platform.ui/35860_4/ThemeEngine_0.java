				String version = t.getOsVersion();
				if (version != null && osVersion.contains(version)) {
					String themeVersion = themeId + version;
					if (t.getId().equals(themeVersion)) {
						setTheme(t, restore);
						found = true;
						break;
