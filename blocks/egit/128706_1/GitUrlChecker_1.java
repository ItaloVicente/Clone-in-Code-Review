		try {
			URIish uri = new URIish(sanitized);
			if (Protocol.FILE.handles(uri)) {
				return sanitized.split("\\v", 2)[0]; //$NON-NLS-1$
			}
		} catch (URISyntaxException e) {
		}
