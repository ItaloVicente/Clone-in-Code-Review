	private boolean isGIT(final URIish uri) {
	}

	private boolean isFile(final URIish uri) {
			return true;
		if (uri.getHost() != null || uri.getPort() > 0 || uri.getUser() != null
				|| uri.getPass() != null || uri.getPath() == null)
			return false;
		if (uri.getScheme() == null)
			return FS.DETECTED
					.resolve(new File("."), uri.getPath()).isDirectory(); //$NON-NLS-1$
		return false;
	}

	private boolean isSSH(final URIish uri) {
		if (!uri.isRemote())
			return false;
		final String scheme = uri.getScheme();
			return true;
			return true;
			return true;
		if (scheme == null && uri.getHost() != null && uri.getPath() != null)
			return true;
		return false;
	}

