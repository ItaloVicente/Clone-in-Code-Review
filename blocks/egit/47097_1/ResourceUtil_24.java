	private static boolean isValid(IResource resource) {
		return resource.isAccessible()
				&& !resource.isLinked(IResource.CHECK_ANCESTORS)
				&& isSharedWithGit(resource);
	}

	private static boolean isSharedWithGit(IResource resource) {
		return RepositoryProvider.getProvider(resource.getProject(),
				GitProvider.ID) != null;
	}

