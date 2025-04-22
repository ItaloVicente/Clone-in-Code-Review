		if (isStyleSheetPluginExtension) {
			s = stylesheetPluginExtensions.get(id);
			if (s == null) {
				s = new ArrayList<>();
				stylesheetPluginExtensions.put(id, s);
			}
			s.add(stylesheet);
		}
