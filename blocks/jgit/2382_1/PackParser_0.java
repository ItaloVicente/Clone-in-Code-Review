	public final PackLock parse(ProgressMonitor progress) throws IOException {
		return parse(progress
	}

	public PackLock parse(ProgressMonitor receiving
			throws IOException {
		if (receiving == null)
			receiving = NullProgressMonitor.INSTANCE;
		if (resolving == null)
			resolving = NullProgressMonitor.INSTANCE;

		if (receiving == resolving)
