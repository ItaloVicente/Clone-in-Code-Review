		public IUriSchemeHandler getHandlerFromExtensionPoint(String uriScheme) throws CoreException {
			if (exception != null) {
				throw exception;
			}
			readCount.putIfAbsent(uriScheme, 0);
			readCount.put(uriScheme, readCount.get(uriScheme) + 1);
			return registration.get(uriScheme);
