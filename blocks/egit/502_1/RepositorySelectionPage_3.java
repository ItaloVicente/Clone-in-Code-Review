		List<URIish> uris;
		if (sourceSelection) {
			uris = rc.getURIs();
		} else {
			uris = rc.getPushURIs();
		}

		for (final URIish u : uris) {
