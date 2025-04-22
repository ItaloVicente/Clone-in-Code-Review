	private void handleResourceChange(GitResourceVariantTreeSubscriber subscriber,
			Repository which, Collection<IFile> resources) {
		for (GitSynchronizeData gsd : gsds) {
			if (which.equals(gsd.getRepository())) {
				if (!resources.isEmpty())
					refreshResources(subscriber, resources);
				else
					refreshRepository(subscriber);
