	public TestRepository(Repository repository) throws IOException {
		this.repository = repository;
		try {
			workdirPrefix = repository.getWorkDir().getCanonicalPath();
		} catch (IOException err) {
			workdirPrefix = repository.getWorkDir().getAbsolutePath();
		}
		workdirPrefix = workdirPrefix.replace('\\', '/');
		if (!workdirPrefix.endsWith("/"))  //$NON-NLS-1$
			workdirPrefix += "/";  //$NON-NLS-1$
	}

