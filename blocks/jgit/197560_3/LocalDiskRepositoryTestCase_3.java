	protected FileRepository createRepositoryWithOptions() throws IOException {
		return createRepository(getOptions());
	}

	protected Options getOptions() {
		return new Options();
	}

