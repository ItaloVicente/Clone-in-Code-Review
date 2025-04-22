	private IResourceVariant getSourceVariant(
			GitResourceVariantTreeSubscriber subscriber, IResource resource,
			boolean includeLocal) throws TeamException {
		IResourceVariantTree tree = subscriber.getSourceTree();
		assertNotNull(tree);
		assertTrue(tree instanceof GitSourceResourceVariantTree);
		IResourceVariant resourceVariant = tree.getResourceVariant(resource);
		assertNotNull(resourceVariant);
		if (includeLocal)
			assertTrue(resourceVariant instanceof GitLocalResourceVariant);
		else
			assertTrue(resourceVariant instanceof GitRemoteResource);
		return resourceVariant;
	}

