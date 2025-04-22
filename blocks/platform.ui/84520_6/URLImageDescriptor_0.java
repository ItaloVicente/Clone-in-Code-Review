			URL tempURL = getURL(url);
			if (tempURL != null) {
				URL xUrl = getxURL(tempURL, zoom);
				return getFilePath(xUrl, zoom == 100); // can be null!
			}
			return null;
