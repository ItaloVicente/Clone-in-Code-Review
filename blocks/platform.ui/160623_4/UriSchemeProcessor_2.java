		handler = getHandler(uriScheme);
		if (handler != null) {
			handler.handle(uri);
		}
	}

	private IUriSchemeHandler getHandler(String uriScheme) throws CoreException {
		IUriSchemeHandler handler;
