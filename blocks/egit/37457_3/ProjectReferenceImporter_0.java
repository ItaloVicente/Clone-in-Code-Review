	private static URIish canonicalizeURL(URIish existingUrl) {
		URIish newURL = existingUrl.setUser(null);

		String path = existingUrl.getPath();
		if (path.endsWith(".git")) { //$NON-NLS-1$
			newURL = existingUrl.setPath(path.substring(0,
					path.lastIndexOf(".git"))); //$NON-NLS-1$
		}

		return newURL;
	}

