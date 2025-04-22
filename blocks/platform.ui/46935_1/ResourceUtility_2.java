		if (!iconPath.scheme().equals("platform")) {
			UrlSchemeHandler urlSchemeHandler = schemeHandler.get(iconPath.scheme());
			if (urlSchemeHandler != null) {
				iconPath = urlSchemeHandler.getPlatformURI(iconPath);
			}
		}

