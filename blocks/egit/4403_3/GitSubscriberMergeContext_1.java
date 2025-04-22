			}
		}
	}

	private void handleResourceChange(GitResourceVariantTreeSubscriber subscriber,
			RepositoryMapping which, IResource resource) {
		for (GitSynchronizeData gsd : gsds) {
			if (which.getRepository().equals(gsd.getRepository())) {
				ResourceMapping adapter = (ResourceMapping) resource
						.getAdapter(ResourceMapping.class);
				if (adapter == null)
					return;
