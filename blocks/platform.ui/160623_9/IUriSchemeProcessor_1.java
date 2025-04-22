
	default void handleUri(URI uri) throws CoreException {
		handleUri(uri.getScheme(), uri.toString());
	}

	boolean canHandle(URI uri);
