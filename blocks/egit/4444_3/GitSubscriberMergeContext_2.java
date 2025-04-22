	private void handleResourceChange(GitResourceVariantTreeSubscriber subscriber,
			Repository which, Collection<IFile> resources) {
		for (GitSynchronizeData gsd : gsds) {
			if (which.equals(gsd.getRepository()) && resources != null
					&& !resources.isEmpty())
				refreshResources(subscriber, resources);
		}
	}
