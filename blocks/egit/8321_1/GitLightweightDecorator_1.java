		IDecoratableResource decoRes;
		try {
			decoRes = new DecoratableResourceMapping(mapping);
		} catch (IOException e) {
			throw new CoreException(Activator.createErrorStatus(UIText.Decorator_exceptionMessage, e));
		}
