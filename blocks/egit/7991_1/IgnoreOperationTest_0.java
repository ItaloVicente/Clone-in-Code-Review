
	private IgnoreOperation executeIgnore(IPath... paths) throws Exception {
		IgnoreOperation operation = new IgnoreOperation(Arrays.asList(paths));
		operation.execute(new NullProgressMonitor());
		return operation;
	}
