				String osVersionList = t.getOsVersion();
				if (osVersionList != null) {
					String[] osVersions = osVersionList.split(","); //$NON-NLS-1$
					for (String osVersionFromTheme : osVersions) {
						if (osVersionFromTheme != null && osVersion.contains(osVersionFromTheme)) {
							String themeVersion = themeId + osVersionList;
							if (t.getId().equals(themeVersion)) {
								setTheme(t, restore);
								found = true;
								break;
							}
						}
