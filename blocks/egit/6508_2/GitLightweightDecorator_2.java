	private void decorateRepository(RepositoryNode node, IDecoration decoration)
			throws CoreException {
		IndexDiffData status = org.eclipse.egit.core.Activator.getDefault()
				.getIndexDiffCache()
				.getIndexDiffCacheEntry(node.getRepository()).getIndexDiff();
		IDecoratableResource decoratable = null;
		final DecorationHelper helper = new DecorationHelper(Activator
				.getDefault().getPreferenceStore());
		try {
			decoratable = new DecoratableRepository(status,
					node.getRepository());
		} catch (IOException e) {
			throw new CoreException(Activator.createErrorStatus(
					UIText.Decorator_exceptionMessage, e));
		}
		helper.decorate(decoration, decoratable);
	}
