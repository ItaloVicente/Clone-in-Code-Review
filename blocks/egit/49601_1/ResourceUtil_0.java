	public static boolean isSharedWithGit(IResource resource) {
		IProject project = resource.getProject();
		if (!project.isAccessible()) {
			return false;
		}
		try {
			RepositoryProvider provider = lookupProviderProp(project);
			if (provider != null) {
				if (GitProvider.ID.equals(provider.getID())) {
					return true;
				} else {
					return false;
				}
			}
			String existingID = project
					.getPersistentProperty(PROVIDER_PROP_KEY);
			return GitProvider.ID.equals(existingID);
		} catch (CoreException e) {
			Activator.getDefault().getLog().log(e.getStatus());
			return false;
		}
	}

	final public static GitProvider getGitProvider(IProject project,
			boolean mapIfNeeded) {
		if (!project.isAccessible()) {
			return null;
		}
		try {
			GitProvider provider = lookupProviderProp(project);
			if (provider != null) {
				if (GitProvider.ID.equals(provider.getID())) {
					return provider;
				} else {
					return null;
				}
			}
			if (!mapIfNeeded) {
				return null;
			}
			return (GitProvider) RepositoryProvider.getProvider(project,
					GitProvider.ID);
		} catch (CoreException e) {
			Activator.getDefault().getLog().log(e.getStatus());
		}
		return null;
	}

	private static GitProvider lookupProviderProp(IProject project)
			throws CoreException {
		Object provider = project.getSessionProperty(PROVIDER_PROP_KEY);
		if (provider instanceof GitProvider) {
			return (GitProvider) provider;
		}
		return null;
