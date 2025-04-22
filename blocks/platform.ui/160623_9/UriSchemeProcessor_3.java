		return handler;
	}

	@Override
	public boolean canHandle(URI uri) {
		try {
			return getHandler(uri.getScheme()) != null;
		} catch (CoreException e) {
			return false;
