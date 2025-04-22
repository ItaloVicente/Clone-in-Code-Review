	private static boolean isValid(IContainer container) {
		return container.isAccessible()
				&& !container.isLinked(IResource.CHECK_ANCESTORS)
				&& isProjectSharedWithGit(container);
	}

