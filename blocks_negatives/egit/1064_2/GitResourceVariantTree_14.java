	@Override
	protected IResourceVariant fetchVariant(IResource resource, int depth,
			IProgressMonitor monitor) throws TeamException {
		try {
			if (resource != null)
				return fetchVariant(resource, monitor);
			else
				return null;
		} finally {
			monitor.done();
		}
