	private IResourceVariant commonAssertionsForRemoteTree(
			IResourceVariantTree baseTree, IResource resource)
			throws TeamException {
		assertNotNull(baseTree);
		assertTrue(baseTree instanceof GitRemoteResourceVariantTree);
		IResourceVariant resourceVariant = baseTree
				.getResourceVariant(resource);
		assertNotNull(resourceVariant);
		assertTrue(resourceVariant instanceof GitRemoteResource);
		return resourceVariant;
	}
