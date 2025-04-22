	@Override
	public Reader getReader(String uri) throws Exception {
		if (StringUtils.isEmpty(uri)) {
			return null;
		}
		if (uriResolvers == null) {
			return null;
		}
		for (IResourceLocator resolver : uriResolvers) {
			String s = resolver.resolve(uri);
			if (s != null) {
				Reader reader = resolver.getReader(uri);
				if (reader  != null) {
					return reader;
				}
			}
		}

		return null;
	}
