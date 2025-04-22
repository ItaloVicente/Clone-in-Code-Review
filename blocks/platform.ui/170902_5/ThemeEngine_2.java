		if (idWithoutVersion != null) { // stylesheetPluginExtensions don't have a os_version; ensure that they will
			List<String> stylesheetPluginExtensionList = stylesheetPluginExtensions.get(idWithoutVersion);
			if (stylesheetPluginExtensionList != null && stylesheetPluginExtensionList.size() > 0) {
				s = new ArrayList<>(s);
				for (String styleSheet : stylesheetPluginExtensionList) {
					if (s.contains(styleSheet) == false) {
						s.add(styleSheet);
					}
				}
