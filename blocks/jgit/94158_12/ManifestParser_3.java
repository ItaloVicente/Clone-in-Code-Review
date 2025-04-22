	static URI normalizeEmptyPath(URI u) {
		if (u.getHost() != null && !u.getHost().isEmpty() &&
			(u.getPath() == null || u.getPath().isEmpty())) {
			try {
				return new URI(u.getScheme()
					u.getUserInfo()
					"/"
			} catch (URISyntaxException x) {
				throw new IllegalArgumentException(x.getMessage()
			}
		}
		return u;
	}





