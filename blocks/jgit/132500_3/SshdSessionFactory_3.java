	@NonNull
	private FilePasswordProvider createFilePasswordProvider(
			KeyPasswordProvider provider) {
		return new PasswordProviderWrapper(provider);
	}

