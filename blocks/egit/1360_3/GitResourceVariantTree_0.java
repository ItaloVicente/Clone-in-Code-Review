	@Override
	protected IResourceVariant fetchVariant(IResource resource, int depth,
			IProgressMonitor monitor) throws TeamException {
		SubMonitor subMonitor = SubMonitor.convert(monitor);
		if (resource == null) {
			subMonitor.done();
			return null;
		}

		subMonitor.beginTask(NLS.bind(
				CoreText.GitResourceVariantTree_fetchingVariant,
				resource.getName()), IProgressMonitor.UNKNOWN);
		try {
			return fetchVariant(resource, subMonitor);
		} finally {
			monitor.done();
		}
	}

	private IResourceVariant fetchVariant(IResource resource,
			IProgressMonitor monitor) throws TeamException {
