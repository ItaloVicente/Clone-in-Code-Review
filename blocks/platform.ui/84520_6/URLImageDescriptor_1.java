			URL tempURL = getURL(url);
			if (tempURL != null) {
				URL xUrl = getxURL(tempURL, zoom);
				return URLImageDescriptor.getImageData(xUrl);
			}
			return null;
