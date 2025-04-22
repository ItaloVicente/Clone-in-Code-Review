	private IResourceVariant getRemoteVariant(
			GitResourceVariantTreeSubscriber subscriber, IResource resource)
			throws TeamException {
		IResourceVariantTree tree = subscriber.getRemoteTree();
		assertNotNull(tree);
		assertTrue(tree instanceof GitRemoteResourceVariantTree);
		IResourceVariant resourceVariant = tree.getResourceVariant(resource);
		assertNotNull(resourceVariant);
		assertTrue(resourceVariant instanceof GitRemoteResource);
		return resourceVariant;
	}
