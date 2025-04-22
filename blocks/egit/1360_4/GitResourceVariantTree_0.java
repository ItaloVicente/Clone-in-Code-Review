	@Override
	protected IResourceVariant fetchVariant(IResource resource, int depth,
			IProgressMonitor monitor) throws TeamException {
		SubMonitor subMonitor = SubMonitor.convert(monitor);
		if (resource == null) {
			subMonitor.done();
