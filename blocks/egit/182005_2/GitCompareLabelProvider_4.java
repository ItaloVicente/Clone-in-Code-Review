	private IResource getResource(ITypedElement element) {
		if (element instanceof IResourceProvider) {
			IResourceProvider resourceProvider = (IResourceProvider) element;
			return resourceProvider.getResource();
		}
		return null;
	}

