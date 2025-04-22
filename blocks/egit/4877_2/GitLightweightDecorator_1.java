		IDecoratableResource decoratableResource = null;
		final DecorationHelper helper = new DecorationHelper(
				activator.getPreferenceStore());
		try {
			decoratableResource = new DecoratableResourceAdapter(indexDiffData, resource);
		} catch (IOException e) {
			handleException(
					resource,
					new CoreException(Activator.createErrorStatus(
							UIText.Decorator_exceptionMessage, e)));
			return;
