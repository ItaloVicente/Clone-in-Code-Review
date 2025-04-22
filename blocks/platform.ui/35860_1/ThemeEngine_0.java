				String osVersionList = t.getOsVersion();
				if (osVersionList != null) {
					String[] osVersions = osVersionList.split(","); //$NON-NLS-1$
					for (String osVersion2 : osVersions) {
						if (osVersion2 != null && osVersion.contains(osVersion2)) {
							String themeVersion = themeId + osVersionList;
							if (t.getId().equals(themeVersion)) {
								setTheme(t, restore);
								found = true;
								break;
							}
						}
