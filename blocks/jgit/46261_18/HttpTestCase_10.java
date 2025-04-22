
	protected static String rewriteUrl(String url
			int newPort) {
		String newUrl = url;
		if (newProtocol != null && !newProtocol.isEmpty()) {
			if (schemeEnd >= 0) {
				newUrl = newProtocol + newUrl.substring(schemeEnd);
			}
		}
		if (newPort > 0) {
			newUrl = newUrl.replaceFirst(":\\d+/"
		} else {
			newUrl = newUrl.replaceFirst(":\\d+/"
		}
		return newUrl;
	}

	protected static URIish extendPath(URIish uri
			throws URISyntaxException {
		String raw = uri.toString();
		String newComponents = pathComponents;
		if (!newComponents.startsWith("/")) {
			newComponents = '/' + newComponents;
		}
		if (!newComponents.endsWith("/")) {
			newComponents += '/';
		}
		int i = raw.lastIndexOf('/');
		raw = raw.substring(0
		return new URIish(raw);
	}
