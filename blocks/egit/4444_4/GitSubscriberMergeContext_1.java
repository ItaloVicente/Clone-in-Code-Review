	private void handleResourceChange(
			GitResourceVariantTreeSubscriber subscriber, IResourceDelta delta) {
		IResourceDelta[] children = delta.getAffectedChildren();
		for (IResourceDelta resourceDelta : children) {
			IResource resource = resourceDelta.getResource();
			RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
			if (mapping == null)
				continue;

			scanDeltaAndRefresh(subscriber, mapping, resourceDelta);
		}
	}
