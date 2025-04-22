		String contributorURI = URIHelper.constructPlatformURI(contributor);
		Resource resource;
		try {
			resource = resourceSet.getResource(uri, true);
		} catch (RuntimeException e) {
			logger.warn(e, "Unable to read model extension from " + uri.toString()); //$NON-NLS-1$
			return;
		}
