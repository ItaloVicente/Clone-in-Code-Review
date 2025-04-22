		return handler;
	}

	@Override
	public boolean canHandle(URI uri) throws CoreException {
		return getHandler(uri.getScheme()) != null;
