		IDecoratableResource decoratableResource = null;
		try {
			decoratableResource = new DecoratableResourceAdapter(indexDiffData, resource);
		} catch (IOException e) {
			throw new CoreException(Activator.createErrorStatus(
					NLS.bind(UIText.Decorator_exceptionMessage, resource), e));
		}
