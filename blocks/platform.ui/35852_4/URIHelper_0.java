	public static boolean isBundleClassUri(String uri) {
		if (uri != null && uri.startsWith(BUNDLECLASS_SCHEMA)) {
			String[] split = uri.substring(BUNDLECLASS_SCHEMA.length()).split("/"); //$NON-NLS-1$
			if (split.length == 2) {
				return true;
			}
		}
		return false;
	}

