	@Override
	protected IResourceVariant fetchVariant(IResource resource, int depth,
			IProgressMonitor monitor) throws TeamException {
		SubMonitor subMonitor = SubMonitor.convert(monitor);
		subMonitor.beginTask(NLS.bind(
				CoreText.GitResourceVariantTree_fetchingVariant,
				resource.getName()), IProgressMonitor.UNKNOWN);
