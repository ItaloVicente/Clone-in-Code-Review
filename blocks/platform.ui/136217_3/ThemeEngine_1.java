	private boolean isVersionMatch(String osVersionList) {
		boolean found = false;
		String osVersion = System.getProperty("os.version");
		if (osVersion != null) {
			if (osVersionList != null) {
				String[] osVersions = osVersionList.split(","); //$NON-NLS-1$
				for (String osVersionFromTheme : osVersions) {
					if (osVersionFromTheme != null) {
						if (osVersion.contains(osVersionFromTheme)) {
							found = true;
							break;
						}
					}
				}
			}
		}
		return found;
	}

