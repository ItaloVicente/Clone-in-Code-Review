		IDecoratableResource decoratableResource = null;
		final DecorationHelper helper = new DecorationHelper(
				Activator.getDefault().getPreferenceStore());
		try {
			decoratableResource = new DecoratableResourceAdapter(indexDiffData, resource);
		} catch (IOException e) {
			throw new CoreException(Activator.createErrorStatus(UIText.Decorator_exceptionMessage, e));
		}
		helper.decorate(decoration, decoratableResource);
	}

	static IndexDiffData getIndexDiffDataOrNull(IResource resource) {
