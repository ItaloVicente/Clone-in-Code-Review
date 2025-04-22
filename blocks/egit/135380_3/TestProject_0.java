		IPath locationBefore = null;
		URI locationURI = description.getLocationURI();
		if (locationURI != null) {
			locationBefore = URIUtil.toPath(locationURI);
		}
		if (locationBefore == null) {
