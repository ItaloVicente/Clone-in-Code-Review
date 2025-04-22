	private static String computeUserAgent() {
		String version;
		final Package pkg = TransportHttp.class.getPackage();
		if (pkg != null && pkg.getImplementationVersion() != null) {
			version = pkg.getImplementationVersion();
		} else {
		}
	}

