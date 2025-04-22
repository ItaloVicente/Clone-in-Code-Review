	public void setHttpConnectionFactory(
			@NonNull HttpConnectionFactory customFactory) {
		if (factoryUsed) {
			throw new IllegalStateException(JGitText.get().httpFactoryInUse);
		}
		factory = customFactory;
	}

	@NonNull
	public HttpConnectionFactory getHttpConnectionFactory() {
		return factory;
	}

