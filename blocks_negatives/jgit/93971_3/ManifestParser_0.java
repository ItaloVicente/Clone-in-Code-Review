		URI baseUri;
		try {
			baseUri = new URI(baseUrl);
		} catch (URISyntaxException e) {
			throw new SAXException(e);
		}
