	static boolean canHandle(final URIish uri) {
		if (!uri.isRemote())
			return false;
		final String scheme = uri.getScheme();
		if ("ssh".equals(scheme))
			return true;
		if ("ssh+git".equals(scheme))
			return true;
		if ("git+ssh".equals(scheme))
			return true;
		if (scheme == null && uri.getHost() != null && uri.getPath() != null)
			return true;
		return false;
	}
